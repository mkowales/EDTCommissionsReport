package com.edt.commissions.vendor;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.edt.commissions.GetReport;
import com.edt.commissions.PrintOut;
import com.edt.commissions.SalesPerson;

public class VerizonCommissions
{
	public VerizonCommissions()
	{
		super();
	}
	
	public boolean process(XSSFWorkbook XSLXworkbook, Workbook XLSworkbook)
	{
		boolean success = false;
		
		ArrayList <SalesPerson> salesList = new ArrayList<SalesPerson>();
		
		GetReport.out.argument("Verizon", "blue");
		
	    double EDT = 0;
		
		if ((null != XSLXworkbook) || (null != XLSworkbook))
		{
			Sheet sheet = (null != XSLXworkbook) ? XSLXworkbook.getSheetAt(0) : XLSworkbook.getSheetAt(0);
		
			GetReport.out.argument("Wokbook is VALID", "red");
			
			XSSFRow row; 
	        XSSFCell cell;
			Iterator<Row> itRows = sheet.rowIterator();
	        
		    int maxRows = sheet.getPhysicalNumberOfRows(),
		    	rows = 1;
		    
		    GetReport.out.argument("ROWS: " + maxRows, "red");
		    
	        while ((itRows.hasNext()) && (rows < maxRows))
		    {
	        	row = (XSSFRow)itRows.next();
	        	
	            Iterator<Cell> itCells = row.cellIterator();
	            
	            SalesPerson sperson = null;
	            
	            while ((itCells.hasNext()) && (rows < maxRows))
	            {
	                cell = (XSSFCell)itCells.next();
	                
//	                cell = workbook.getSheetAt(sheetNum).getRow(rows).getCell(1);
//	                PrintOut.argument(cell.getStringCellValue(), "brown");
	                
	                boolean found = false;
	                
	                if (salesList.size() == 0)
	                	salesList.add(new SalesPerson(cell.getStringCellValue()));
	                
	                Iterator<SalesPerson> itr = salesList.iterator();
	                
	                while (itr.hasNext())
	                {
	                	sperson = (SalesPerson)itr.next();
	                	found = sperson.compare(cell.getStringCellValue());
	                }
	                
	                if (!found)
	                {
	                	PrintOut.log("NOT FOUND: " + salesList.size());
	                	salesList.add(sperson = new SalesPerson(cell.getStringCellValue()));
	                }
	                
//	                cell = workbook.getSheetAt(sheetNum).getRow(rows++).getCell(9);
//	                PrintOut.argument(String.valueOf(cell.getNumericCellValue()) + " / " + rows, "brown");
	                sperson.setAmount(cell.getNumericCellValue());
	                EDT += cell.getNumericCellValue();
	            }
	        }
	        
			NumberFormat formatter = NumberFormat.getCurrencyInstance();
			
			Iterator<SalesPerson> itr = salesList.iterator();
	        
			GetReport.out.tableStart();
			
	        while (itr.hasNext())
	        {
	        	SalesPerson sp = (SalesPerson)itr.next();
	        	GetReport.out.argument(sp.getName() + ": " + formatter.format(sp.getAmount()), "blue");
	        	GetReport.out.tableRow(sp.getName(), formatter.format(sp.getAmount()));
	        }
	        
	        GetReport.out.argument("EDT = " + formatter.format(EDT * .82), "blue");
	        GetReport.out.tableRow("EDT", formatter.format(EDT * .82));
	        GetReport.out.tableClose();
		}
		else
			GetReport.out.argument("Wokbook is NULL", "red");

		return success;
	}
}