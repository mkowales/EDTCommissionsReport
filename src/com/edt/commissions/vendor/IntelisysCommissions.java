package com.edt.commissions.vendor;

import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.edt.commissions.GetReport;
import com.edt.commissions.SalesPerson;

public class IntelisysCommissions
{
	public IntelisysCommissions()
	{
		super();
	}

	public boolean process(XSSFWorkbook XSLXworkbook, Workbook XLSworkbook)
	{
		boolean success = false;
		
		if ((null != XSLXworkbook) || (null != XLSworkbook))
		{
			Sheet sheet = (null != XSLXworkbook) ? XSLXworkbook.getSheetAt(1) : XLSworkbook.getSheetAt(1);
		    
		    double total = 0,
		    	   amount = 0;
		    
		    String accountName = null,
		    	   supplierName = null,
		    	   sales = null,
		    	   suppCust = null;
		    
		    ArrayList <SalesPerson> salesPeople = null;
		    
		    SalesPerson salesPerson =  null;
		    
		    int lastRow = sheet.getLastRowNum();
//		    int count = 0;
		    
	    	if (null == salesPeople)
			    salesPeople = new ArrayList<SalesPerson>();
		    
//		    traverse through the rows at the 1st column
		    for (int rowNum = 1; rowNum < (lastRow - 1); rowNum++)
		    {
		    	if (null == sheet.getRow(rowNum))
		    		continue;
		    	
		    	Cell accountCell = sheet.getRow(rowNum).getCell(0),
		    		 supplierCell = sheet.getRow(rowNum).getCell(3);
		    	
//		    	use column A to get the supplier name
		    	supplierName = (null != supplierCell) ? supplierCell.toString() : null;
//		    	use column D to get the account name
		    	accountName = (null != accountCell) ? accountCell.toString() : null;
		    	
	    		sales = sheet.getRow(rowNum).getCell(1).toString();
	    		
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
	    		
	    		if (null == supplierName)
	    		{
	    			if (null == accountName)
	    				suppCust = "&nbsp";
	    			else
	    				suppCust = accountName;
	    		}
	    		
	    		else if (null == accountName)
	    			suppCust = supplierName;
	    		
	    		else
	    			suppCust = supplierName + " / " + accountName;
	    		
	    		Cell amountCell = sheet.getRow(rowNum).getCell(9);
	    		
//	    		if something exists in column J
	    		if (null != amountCell)
    			{
//	    			get the value of column J
    				amount = amountCell.getNumericCellValue();
    				
//    				if the current account name is not in the salesPerson list, set it
    				if (salesPerson.isAccountNameInList(sales, suppCust))
    					salesPerson.setAccountAmount(suppCust, amount);
    				else
    					salesPerson.setAccounts(suppCust, amount);
    				
    				salesPerson.setAmount(amount * .18);
    				
//    				if salesPerson is not part of salesPeople, add it
	    			if (!salesPeople.contains(salesPerson))
	    				salesPeople.add(salesPerson);
	    			
//	    			PrintOut.log("IntelisysCommissions EDT IS " + (total * .82) + "; SP IS " + (amount * .18));
	    			total += amount;
	    			
	    			accountName = null;
	    			supplierName = null;
	    		}
	    		
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