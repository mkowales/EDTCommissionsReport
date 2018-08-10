package com.edt.commissions.vendor;

import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.edt.commissions.GetReport;
import com.edt.commissions.PrintOut;
import com.edt.commissions.SalesPerson;

public class CNSGCommissions
{
	public CNSGCommissions()
	{
		super();
	}
	
	public boolean process(XSSFWorkbook XSLXworkbook, Workbook XLSworkbook)
	{
		boolean success = false;
		
		PrintOut.log("CNSG process");
		
		if ((null != XSLXworkbook) || (null != XLSworkbook))
		{
			Sheet sheet = (null != XSLXworkbook) ? XSLXworkbook.getSheetAt(0) : XLSworkbook.getSheetAt(0);
			
			PrintOut.log("CNSG null !- workbook");
			
			final String ADJUSTMENT = "(adjustment)";
			
		    double total = 0,
		    	   amount = 0;
		    
		    String supplierName = null,
		    	   customerName = null,
		    	   salesName = null,
		    	   suppCust = null;
		    
		    ArrayList <SalesPerson> salesPeople = null;
		    
		    SalesPerson salesPerson =  null;
		    
		    int lastRow = sheet.getLastRowNum();
		    
	    	if (null == salesPeople)
			    salesPeople = new ArrayList<SalesPerson>();
		    
//		    traverse through the rows at the 1st column
		    for (int rowNum = 1; rowNum < (lastRow + 1); rowNum++)
		    {
		    	if (null == sheet.getRow(rowNum))
		    		continue;
		    	
		    	Cell salesCell = sheet.getRow(rowNum).getCell(1),		// get the sales person name
		    		 amountCell = sheet.getRow(rowNum).getCell(8),		// get the commission
		    		 supplierCell = sheet.getRow(rowNum).getCell(0),	// get the suppler name
		    		 customerCell = sheet.getRow(rowNum).getCell(3);	// get the customer name
		    	
//		    	use column A to get the supplier name
		    	supplierName = (null != supplierCell) ? supplierCell.getStringCellValue() : null;
//		    	use column B to get the sales name
		    	salesName = (null != salesCell) ? salesCell.getStringCellValue() : null;
//		    	use column C to get the customer name
		    	customerName = (null != customerCell) ? customerCell.getStringCellValue() : null;
//		    	use column I to get the amount
		    	amount = (null != amountCell) ? amountCell.getNumericCellValue() : null;
//		    	amountStr = (null != amountCell) ? amountCell.getStringCellValue() : null;
		    	
//			    the salespeople are the column B
	    		if ((null == salesPerson) || (0 == salesPeople.size()) || (salesPeople.isEmpty()))
		    		salesPerson =  new SalesPerson(salesName);
	    		else
	    		{
	    			boolean found = false;
	    			
	    			int index = 0;
	    			
//				    if the salesperson does not exist in the list now, add him/her
		    		for (index = 0; index < salesPeople.size(); index++)
		    		{
		    			if (true == (found = salesPeople.get(index).getName().equals(salesName)))
		    			{
		    				salesPerson = salesPeople.get(index);
		    				break;
		    			}
		    		}
		    			
		    		if ((!found) && (index == salesPeople.size()))
		    			salesPerson =  new SalesPerson(salesName);
	    		}
	    		
	    		if (null == supplierName)
	    		{
	    			if (null == customerName)
	    				suppCust = "&nbsp";
	    			else
	    				suppCust = customerName;
	    		}
	    		
	    		else if ((null == customerName) || (ADJUSTMENT.equals(customerName)))
	    			suppCust = supplierName;
	    		
	    		else
	    			suppCust = supplierName + " / " + customerName;
	    		
//	    		if something exists in column I
	    		if (null != amountCell)
    			{
//    				if the current account name is not in the salesPerson list, set it
    				if (salesPerson.isAccountNameInList(salesName, suppCust))
    					salesPerson.setAccountAmount(suppCust, amount);
    				else
    					salesPerson.setAccounts(suppCust, amount);

    				if (ADJUSTMENT.equals(salesPerson.getName()))
    					salesPerson.setAmount(amount);
    				else
    					salesPerson.setAmount(amount * .18);
    				
//    				if salesPerson is not part of salesPeople, add it
	    			if (!salesPeople.contains(salesPerson))
	    				salesPeople.add(salesPerson);
	    			
	    			PrintOut.log("CNSGCommissions EDT for " + customerName + " (" + total + "/" + amount + ") IS " + (amount * .82) + "; SP IS " + (amount * .18));
	    			total += amount;		// $846.73
	    		}
	    	}
		    
        	salesPerson = new SalesPerson("EagleDream");
        	salesPerson.setAmount(total * .82);
        	salesPeople.add(salesPerson);
        	
        	GetReport.out.tableRows(salesPeople, total);
        	
        	GetReport.out.close();
		}
		else
			PrintOut.log("CNSG workbook IS null");
		
		return success;
	}
}