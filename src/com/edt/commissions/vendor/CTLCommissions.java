package com.edt.commissions.vendor;

import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.edt.commissions.GetReport;
import com.edt.commissions.SalesPerson;

public class CTLCommissions
{
	public CTLCommissions()
	{
		super();
	}
	
	public boolean process(XSSFWorkbook XSLXworkbook, Workbook XLSworkbook)
	{
		boolean success = false;
		
		if ((null != XSLXworkbook) || (null != XLSworkbook))
		{
//			GetReport.out.setDebugger(true);
//			GetReport.out.log("CTLCommissions Workbook is VALID");

		    Sheet sheet = (null != XSLXworkbook) ? XSLXworkbook.getSheetAt(0) : XLSworkbook.getSheetAt(0);
		    
		    double total = 0,
		    	   amount = 0;
		    
		    String revenueClass = null,
		    	   customerName = null,
		    	   sales = "Sales Person",
		    	   suppCust = null;
		    
		    ArrayList <SalesPerson> salesPeople = null;
		    
		    SalesPerson salesPerson =  null;
		    
		    int lastRow = sheet.getLastRowNum();
		    
	    	if (null == salesPeople)
			    salesPeople = new ArrayList<SalesPerson>();
	    	
	    	boolean topRow = false;
	    	
//	    	int count = 0;
	    	
	    	CellType cte = null;
		    
//		    traverse through the rows at the 16tn column
		    for (int rowNum = 0; rowNum < (lastRow - 1); rowNum++)
		    {
		    	if (null == sheet.getRow(rowNum))
		    		continue;
		    	
		    	Cell customerCell = sheet.getRow(rowNum).getCell(16);
		    	
//		    	PrintOut.log("ROW " + rowNum + " ->> " + ((null != customerCell) ? customerCell.getStringCellValue() : "null"));
		    	
		    	if (!topRow)
		    	{
		    		if ((null != customerCell) && ("STRING".equals(customerCell.getCellTypeEnum().toString())))
			    			topRow = true;
		    		
		    		continue;
		    	}
		    	
		    	if (null == customerCell)
		    		continue;
		    	
		    	customerName = customerCell.getStringCellValue();
		    	
		    	if (customerName.isEmpty())
		    		continue;
		    	
		    	Cell revenueClassCell = sheet.getRow(rowNum).getCell(27),
		    		 adjustedComRevAmount = sheet.getRow(rowNum).getCell(55);
		    	
//		    	use column AB to get the revenue class
		    	revenueClass = (null != revenueClassCell) ? revenueClassCell.toString() : null;

//		    	use column BD to get the adjusted revenue amount
		    	if (null == adjustedComRevAmount)
		    		continue;
		    	
		    	cte = adjustedComRevAmount.getCellTypeEnum();
		    	
		    	if (cte.equals(CellType.BLANK))
		    		continue;
		    	
		    	amount = (null != adjustedComRevAmount) ? adjustedComRevAmount.getNumericCellValue() : null;
		    	
		    	if (0 == amount)
		    		continue;
		    	
//			    the salespeople are in the column B
	    		if ((null == salesPerson) || (0 == salesPeople.size()) || (salesPeople.isEmpty()))
		    		salesPerson =  new SalesPerson(sales);
	    		else
	    		{
	    			boolean found = false;
	    			
	    			int index = 0;
	    			
//				    if the salesperson does not exist in the list now, add him/her
		    		for (index = 0; index < salesPeople.size(); index++)
		    		{
		    			if (true == (found = salesPeople.get(index).getName().equals(sales)))
		    			{
		    				salesPerson = salesPeople.get(index);
		    				break;
		    			}
		    		}
		    			
		    		if ((!found) && (index == salesPeople.size()))
		    			salesPerson =  new SalesPerson(sales);
	    		}
	    		
	    		if (null == revenueClass)
	    			suppCust = customerName;
	    		else
	    			suppCust = customerName + " / " + revenueClass;
	    		
//    			if the current account name is not in the salesPerson list, set it
				if (salesPerson.isAccountNameInList(sales, suppCust))
					salesPerson.setAccountAmount(suppCust, amount);
				else
					salesPerson.setAccounts(suppCust, amount);
				
				salesPerson.setAmount(amount * .18);
				
//    			if salesPerson is not part of salesPeople, add it
    			if (!salesPeople.contains(salesPerson))
    				salesPeople.add(salesPerson);
    			
//    			PrintOut.log("CTLCommissions EDT IS " + (total * .82) + "; SP IS " + (amount * .18));
    			total += amount;
    			
    			revenueClass = null;
    			customerName = null;
	    		
//	    		if (count++ > 10)
//	    			break;
	    	}
		    
        	salesPerson = new SalesPerson("EagleDream");
        	salesPerson.setAmount(total * .82);
        	salesPeople.add(salesPerson);
        	
        	GetReport.out.tableRows(salesPeople, total);
        	
        	salesPerson = null;
		    salesPeople = null;
		    amount = 0;
		    total = 0;
		    
		    GetReport.out.close();
		}
		
		return success;
	}
}