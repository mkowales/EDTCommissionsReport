package com.edt.commissions.vendor;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.edt.commissions.GetReport;
import com.edt.commissions.SalesPerson;

public class CaleroCommissions
{
	public CaleroCommissions()
	{
		super();
	}
	
	public boolean process(XSSFWorkbook XSLXworkbook, Workbook XLSworkbook)
	{
		boolean success = false;
		
		if ((null != XSLXworkbook) || (null != XLSworkbook))
		{
			Sheet sheet = (null != XSLXworkbook) ? XSLXworkbook.getSheetAt(0) : XLSworkbook.getSheetAt(0);

//			PrintOut.argument("CaleroCommissions Workbook is VALID", "red");
			
		    double total = 0,
		    	   amount = 0;
		    
		    ArrayList <SalesPerson> salesPeople = null;
		    
		    SalesPerson salesPerson =  null;
		    
		    int lastRow = 0;
		    
//		    traverse through the sheets in the workbook
//		    for (int sheetCount = 0; sheetCount < workbook.getNumberOfSheets(); sheetCount++)
		    {
//		    	GetReport.out.setDebugger(true);
//		    	PrintOut.log("CaleroCommissions MAIN FOR LOOP: " + sheetCount + " / " + workbook.getNumberOfSheets());
		    	
		    	if (null == salesPeople)
				    salesPeople = new ArrayList<SalesPerson>();
			    
		    	if (null == salesPerson)
		    		salesPerson =  new SalesPerson("Sales Person");
		    	
//		    	sheet = workbook.getSheetAt(sheetCount);
		    	
		    	lastRow = sheet.getLastRowNum();
		    	
//		    	PrintOut.log("CaleroCommissions LASTROW: " + sheet.getLastRowNum());
		    	
//		    	traverse throw the rows
			    for (int count = 1; count < (lastRow - 1); count++)
			    {
			    	Cell accountCell = sheet.getRow(count).getCell(0);
			    	
			    	if (null == accountCell)
			    		continue;
			    	
//			    	find the 1st cell in column A that is Customer
			    	if ((null != accountCell.getStringCellValue()) && (!"Customer".equals(accountCell.getStringCellValue())))
			    		continue;
			    	
//			    	only get here when we see the "Customer" cell
		    		accountCell = sheet.getRow(count += 2).getCell(0);
			    	
//			    	find the next cells that come after Customer -> they are account names
		    		for (int currRow = count; (currRow < lastRow) && (null != accountCell); currRow++)
		    		{
//			    		starting at the current row, find the 1st "open" in column I
		    			for (int statRow = currRow; (statRow < lastRow) && (null != accountCell); statRow++)
		    			{
		    				Cell amountCell = sheet.getRow(statRow).getCell(7);
			    			Cell statusCell = sheet.getRow(statRow).getCell(8);
			    			
				    		if (("Open".equals(statusCell.toString()) && (null != amountCell)))
				    		{
//	 							back up a column on the same row
			    				amount = amountCell.getNumericCellValue();
			    				
			    				salesPerson.setAccounts(accountCell.toString(), amount);
			    				total += amount;
			    				accountCell = null;
				    		}
		    			}
		    		}
			    }
			    
			    salesPerson.setAmount(total * .18);
	        	salesPeople.add(salesPerson);
	        	
	        	salesPerson = new SalesPerson("EagleDream");
	        	salesPerson.setAmount(total * .82);
	        	salesPeople.add(salesPerson);
	        	
	        	GetReport.out.tableRows(salesPeople, total);
	        	
	        	salesPerson = null;
			    salesPeople = null;
			    total = 0;
//		    
			    GetReport.out.close();
		    }
		}
		
		return success;
	}
}