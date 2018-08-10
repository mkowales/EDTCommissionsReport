package com.edt.commissions.vendor;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.edt.commissions.GetReport;
import com.edt.commissions.SalesPerson;

public class WACommissions
{
	public WACommissions()
	{
		super();
	}
	
	public boolean process(XSSFWorkbook XSLXworkbook, Workbook XLSworkbook)
	{
		boolean success = false;
		
		if ((null != XSLXworkbook) || (null != XLSworkbook))
		{
			Sheet sheet = (null != XSLXworkbook) ? XSLXworkbook.getSheetAt(0) : XLSworkbook.getSheetAt(0);

//			int sheetNum = 0;
		    
		    double total = 0,
		    	   amount = 0;
		    
		    String accountName = null;
		    
		    ArrayList <SalesPerson> salesPeople = null;
		    
		    ArrayList <String> salesAccounts = null;
		    
		    SalesPerson salesPerson =  null;
		    
		    int lastRow = 0;
		    
//		    for (int sheetCount = 0; sheetCount < workbook.getNumberOfSheets(); sheetCount++)
		    {
		    	int saveCol = 0;
		    	
		    	if (null == salesPeople)
				    salesPeople = new ArrayList<SalesPerson>();
			    
			    if (null == salesAccounts)
			    	salesAccounts = new ArrayList<String>();
			    
		    	if (null == salesPerson)
		    		salesPerson =  new SalesPerson("Sales Person");
		    	
//		    	sheet = workbook.getSheetAt(sheetCount);
		    	
		    	lastRow = sheet.getLastRowNum();
		    	
//		    	traverse through the rows at the 1st column
			    for (int rowNum = 0; rowNum < (lastRow - 1); rowNum++)
			    {
			    	if (null == sheet.getRow(rowNum))
			    		continue;
			    	
			    	accountName = sheet.getRow(rowNum).getCell(0).toString();
			    	
//			    	find the 1st row w/ Invoice in the 1st column
			    	if ("Invoice".equals(accountName))
			    	{
//			    		back uo a row to get the account name
			    		accountName = sheet.getRow(rowNum - 1).getCell(0).toString();
			    		
//			    		search the Invoice row for the 1st BLANK column
		    			salesAccounts.add(accountName);
			    		
//			    		find the 1st invoice that is blank
		    			for (int col = 1; col < sheet.getRow(rowNum).getPhysicalNumberOfCells(); col++)
		    			{
			    			Cell amountCell = sheet.getRow(rowNum).getCell(col);
			    							    			
//				    		traverse through the columns for blank; skip invoice numbers
				    		if ((null != amountCell.toString()) && (0 < amountCell.toString().length()))
				    			continue;

//				    		back up a row for the amount
				    		amountCell = sheet.getRow(rowNum - 1).getCell(col );
				    			
				    		if (null != amountCell)
			    			{
			    				amount = amountCell.getNumericCellValue();
			    				
				    			salesPerson.setAccounts(accountName, amount);
				    			
//				    			the total amount is at the same column on the last row
				    			amount = sheet.getRow(lastRow).getCell(col).getNumericCellValue();
				    			
				    			if (col != saveCol)
				    			{
				    				total += amount;
				    				saveCol = col;
				    			}
				    			
				    			accountName = null;
				    			break;
			    			}
			    		 }
			    		
//		    			if (sheetNum < workbook.getNumberOfSheets())
//		    				sheet = workbook.getSheetAt(sheetNum++);
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
			    salesAccounts = null;
			    total = 0;
		    }
		    
		    GetReport.out.close();
		}
		
		return success;
	}
}