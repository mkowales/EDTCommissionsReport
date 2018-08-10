package com.edt.commissions.vendor;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.edt.commissions.GetReport;

public class WinCommissions
{
	public WinCommissions()
	{
		super();
	}
	
	public boolean process(XSSFWorkbook workbook)
	{
		boolean success = false;
		
		GetReport.out.argument("Win Analytics", "blue");
		
		if (null != workbook)
			GetReport.out.argument("Wokbook is VALID", "red");
		else
			GetReport.out.argument("Wokbook is NULL", "red");
		
		return success;
	}
}