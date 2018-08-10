package com.edt.commissions;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.edt.commissions.vendor.CNSGCommissions;
import com.edt.commissions.vendor.CTLCommissions;
import com.edt.commissions.vendor.CaleroCommissions;
import com.edt.commissions.vendor.IntelisysCommissions;
import com.edt.commissions.vendor.VerizonCommissions;
import com.edt.commissions.vendor.WACommissions;
import com.edt.commissions.vendor.WinCommissions;

/**
 * Servlet implementation class Hello
 */
@WebServlet("/GetReport")
@MultipartConfig

public class GetReport extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public static PrintOut out = null;
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public GetReport()
    {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		response.setContentType("text/html");
		
		out = new PrintOut(response.getWriter());
		
		out.argument("<html><body>", "red");
		out.argument("<h3>Hello World!!</h3>", "red");
		out.argument("</body></html>", "red");
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException
	{
	    System.out.println("doPost");
	    
		out = new PrintOut(response.getWriter());
		
        response.setContentType("text/html");
        
        this.handleRequest(request);

        out.close();
	}
	
	protected void handleRequest(HttpServletRequest request)
	{
//        MainProcess main = new MainProcess();
        
        Enumeration<String> parameterNames = request.getParameterNames();
        
		try
		{
			Part filePart = request.getPart("file");
			
			String fileName = this.getFileName(filePart);
			
			PrintOut.log("FileName is " + fileName);
			
	        while (parameterNames.hasMoreElements())
	        {
	        	String paramName = parameterNames.nextElement();

	            String[] paramValues = request.getParameterValues(paramName);
			
	            for (int i = 0; i < paramValues.length; i++)
	            {
	                String paramValue = paramValues[i];
	                
	                System.out.println(i + ". " + paramValue);
	                
	                XSSFWorkbook XLSXworkbook = null;
        			Workbook XLSworkbook = null;
        			
        			if ("comType".equalsIgnoreCase(paramName))
	                {
	                	switch (paramValue)
	                    {
	                    	case "Calero":
	                    		if (fileName.startsWith("Calero"))
	                    		{
	                    			PrintOut.log(paramValue);
	                    			
	                    			CaleroCommissions comCo = new CaleroCommissions();
	                    			
	                    			if (fileName.endsWith(".xls"))
	                    				XLSworkbook = new HSSFWorkbook(filePart.getInputStream());
	                    			else
	                    				XLSXworkbook = new XSSFWorkbook(filePart.getInputStream());
	 
	                    			comCo.process(XLSXworkbook, XLSworkbook);
	                    		}
	                    		else
	                    			processError(paramValue, "Calero");
	                    		break;
	                    		
	                    	case "Century Link":
	                    		if (fileName.startsWith("CTL"))
	                    		{
	                    			PrintOut.log(paramValue);
	                    			
	                    			CTLCommissions comCo = new CTLCommissions();
	                    			
	                    			if (fileName.endsWith(".xls"))
	                    				XLSworkbook = new HSSFWorkbook(filePart.getInputStream());
	                    			else
	                    				XLSXworkbook = new XSSFWorkbook(filePart.getInputStream());
	                    			
	                    			comCo.process(XLSXworkbook, XLSworkbook);
	                    		}
	                    		else
	                    			processError(paramValue, "CTL");
	                    		break;
	                    	
	                    	case "CNSG":
	                    		if (fileName.startsWith("CNSG"))
	                    		{
	                    			PrintOut.log(paramValue);
	                    			
	                    			if (fileName.endsWith(".xls"))
	                    				XLSworkbook = new HSSFWorkbook(filePart.getInputStream());
	                    			else
	                    				XLSXworkbook = new XSSFWorkbook(filePart.getInputStream());
	                    			
	                    			CNSGCommissions comCo = new CNSGCommissions();
	                    			
	                    			comCo.process(XLSXworkbook, XLSworkbook);
	                    		}
	                    		else
	                    			processError(paramValue, "CNSG");
	                    		break;
	                    		
	                    	case "Intelisys":
	                    		if (fileName.startsWith("Intelisys"))
	                    		{
	                    			PrintOut.log(paramValue);
	                    			
	                    			IntelisysCommissions comCo = new IntelisysCommissions();
                    			
	                    			if (fileName.endsWith(".xls"))
	                    				XLSworkbook = new HSSFWorkbook(filePart.getInputStream());
	                    			else
	                    				XLSXworkbook = new XSSFWorkbook(filePart.getInputStream());
	 
	                    			comCo.process(XLSXworkbook, XLSworkbook);
	                    		}
	                    		else
	                    			processError(paramValue, "Intelisys");
	                    		break;
	                    		
	                    	case "Verizon":
	                    		if (fileName.startsWith("Verizon"))
	                    		{
	                    			PrintOut.log(paramValue);
	                    			
	                    			VerizonCommissions comCo = new VerizonCommissions();
	                    			
	                    			if (fileName.endsWith(".xls"))
	                    				XLSworkbook = new HSSFWorkbook(filePart.getInputStream());
	                    			else
	                    				XLSXworkbook = new XSSFWorkbook(filePart.getInputStream());
	 
	                    			comCo.process(XLSXworkbook, XLSworkbook);
	                    		}
	                    		else
	                    			processError(paramValue, "Verizon");
//	                    		main.processVerizon(filePart);
	                    		break;
	                    		
	                    	case "Windstream":
	                    		if (fileName.startsWith("Windstream"))
	                    		{
	                    			PrintOut.log(paramValue);
	                    			
	                    			WinCommissions comCo = new WinCommissions();
	                    			
	                    			InputStream inputStream = filePart.getInputStream();
	                    			
	                    			if (fileName.endsWith(".xls"))
	                    				XLSworkbook = new HSSFWorkbook(inputStream);
	                    			else
	                    				XLSXworkbook = new XSSFWorkbook(inputStream);
	 
	                    			comCo.process(new XSSFWorkbook(filePart.getInputStream()));
	                    		}
	                    		else
	                    			processError(paramValue, "Windstream");
//	                    		main.processWIN(filePart);
	                    		break;
	                    		
	                    	case "Wireless Analytics":
	                    		if (fileName.startsWith("Wireless"))
	                    		{
	                    			PrintOut.log(paramValue);
	                    			
	                    			WACommissions comCo = new WACommissions();
	                    			
	                    			if (fileName.endsWith(".xls"))
	                    				XLSworkbook = new HSSFWorkbook(filePart.getInputStream());
	                    			else
	                    				XLSXworkbook = new XSSFWorkbook(filePart.getInputStream());
	 
	                    			comCo.process(XLSXworkbook, XLSworkbook);
	                    		}
	                    		else
	                    			processError(paramValue, "Wireless");
	                    		break;
	                    		
	                    	default:
	                    		break;
	                    }
	                }
	                
	                out.argument(paramValue, "green");
	            }
            } 
        }
		catch (IOException | ServletException e1)
		{
			e1.printStackTrace();
        }
	}
	
	private void processError(String paramValue, String app)
	{
		out.argumentOne("Incorrect file for <I>" + paramValue +
				"</I>.<BR>Please use a spreadsheet that starts with <B>" + app + 
				"</B> & adheres to its standards.", "red");
	}
	
	public String getFileName(final Part part)
    {
        final String partHeader = part.getHeader("content-disposition");
        
        PrintOut.log("Part Header " + partHeader);
        
        for (String content : part.getHeader("content-disposition").split(";"))
        {
            if (content.trim().startsWith("filename"))
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
        }
        
        return null;
    }
}
