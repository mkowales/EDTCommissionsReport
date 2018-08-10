package com.edt.commissions;

import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class PrintOut
{
	private PrintWriter out = null;
	
	private boolean DEBUGGER = false;
	
	public PrintOut(PrintWriter pw)
	{
		if (null != pw)
		{
			this.out = pw;
			this.out.println("<html>");
			
//			this.out.println("<script src=\"https://www.w3schools.com/lib/w3.js\"></script>");
//			this.out.println("<div w3-include-html=\"http://localhost/~mkowales/edt/header.html\"></div>");
//			this.out.println("<script>w3.includeHTML();</script>");

			this.out.println("<head>");
			this.out.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js\"></script>");
			this.out.println("<link rel=\"icon\" href=\"https://edtssl.eagledream-hosting.com/wp-content/uploads/2015/02/edt_fav_2.png\"/>");
			this.out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
			this.out.println("</head>");
			this.out.println("<body>");
			this.out.println("<style>");
			this.out.println("h2");
			this.out.println("{");
			this.out.println("   position: absolute;");
			this.out.println("   top: 35%;");
			this.out.println("   right: 50%;");
			this.out.println("   left: 25%;");
			this.out.println("   width: 50%;");
			this.out.println("   color: white;");
			this.out.println("}");
			this.out.println("</style>");
			this.out.println("<title>EDT - Commissions Report</title>");
			this.out.println("<center>");
			this.out.println("<div id=\"under\" style=\"left: 0px; top: 0px; position: relative\">");
			this.out.println("<img src=\"https://cdn-images.9cloud.us/8/dreamy-dre_245154186.jpg\" width=\"50%\" height=\"40%\">");
			this.out.println("</div>");
			this.out.println("<div id=\"top\" style=\"left: 250px; top: 25px; position: absolute\">");
			this.out.println("<img src=\"https://d3rdz1nqt7l291.cloudfront.net/wp-content/uploads/2015/02/edt_logo_white_2-e1454088284511.png?x59865\" width=\"50%\">");
			this.out.println("</div>");
			
//			this.out.println("<span>");
//			this.out.println("<style>#fancy-title-3 {font-family: \"Open Sans\"}</style>");
//			this.out.println("<h2>Commissions Report</h2>");
		}
	}
	
	public void setDebugger(boolean test)
	{
		this.DEBUGGER = test;
	}
	
	public boolean getDebugger()
	{
		return this.DEBUGGER;
	}
	
	public void argument(String str, String color)
	{
		if (this.DEBUGGER)
		{
			this.out.write("<font color=\"" + color + "\"><b>");
			this.out.write("- " + str);
			this.out.write("</font></b><br>");
		}
	}
	
	public void argumentOne(String str, String color)
	{
		this.out.write("<font color=\"" + color + "\"><b>");
		this.out.write(str);
		this.out.write("</font></b>");
	}
	
	public void close()
	{
//		this.out.println("<div w3-include-html=\"http://localhost/~mkowales/edt/footer.html\"></div>");
		this.out.println("<hr>");
		this.out.println("<input type=\"button\" value=\"Back\" onclick=\"window.history.back()\"/>");
		this.out.println("</center>");
		this.out.println("</body></html>");
		this.out.close();
		
//		this.out.println("<hr>");
//		this.out.println("<input type=\"button\" value=\"Back\" onclick=\"window.history.back()\"/>");
	}
	
	public void tableRow(double total, double EDT, double sales)
	{
		this.out.println("<table border=\"1\" cellpadding=\"10\">");
		this.out.println("<tr><th bgcolor=\"cyan\">Commisionee</th><th bgcolor=\"cyan\">Amount</th></tr>");
		this.out.println("<tr><td><font color=\"blue\">EagleDream</font></td><td><b>" + EDT + "</b></td><tr>");
		this.out.println("<tr><td><font color=\"green\">Sales Person</font></td><td><b>" + sales + "</b></td><tr>");
		this.out.println("<tr><td><font color=\"red\">Total</font></td><td><i>" + total + "</i></td><tr>");
		this.out.println("<table>");
	}

	public void tableRow(double total, double EDT, double sales, String account)
	{
		this.out.println("<table border=\"1\" cellpadding=\"10\">");
		this.out.println("<tr><th bgcolor=\"cyan\">Commisionee</th><th bgcolor=\"cyan\">Amount</th></tr>");
		this.out.println("<tr><td><font color=\"blue\">EagleDream</font></td><td><b>" + EDT + "</b></td><tr>");
		this.out.println("<tr><td><font color=\"green\">Sales Person</font></td><td><b>" + sales + "</b></td><tr>");
		
		if (null != account)
			this.out.println("<tr><td><font color=\"green\">" + account + "</font></td><td><b>&nbsp</b></td><tr>");
		
		this.out.println("<tr><td><font color=\"red\">Total</font></td><td><i>" + total + "</i></td><tr>");
		this.out.println("<table>");
	}

	public void tableStart()
	{
		this.out.println("<table border=\"1\" cellpadding=\"10\">");
		this.out.println("<tr><th bgcolor=\"cyan\">Recipient</th><th bgcolor=\"cyan\">Amount</th></tr>");
	}
	
	public void tableRow(String sname, String amount)
	{
		this.out.println("<tr><td><font color=\"green\">" + sname + "</font>:</td><td><b>" + amount + "</b></td><tr>");
	}

	public void tableClose()
	{
		this.out.println("</table>");
	}

	public static void log(String str)
	{
		System.out.println(str);
	}

	public void tableRows(ArrayList<SalesPerson> salesPeople, double total)
	{
		DecimalFormat df = new DecimalFormat("#,###.00");
    	
		this.out.println("<center>");
		this.out.println("<table border=\"1\" cellpadding=\"10\" width=\"50%\"");
		this.out.println("<tr><th bgcolor=\"cyan\">Recipient</th><th bgcolor=\"cyan\">Amount</th></tr>");

		for (int counter = 0; counter < salesPeople.size(); counter++)
		{
			if ("EagleDream".equals(salesPeople.get(counter).getName()))
			{
				this.out.println("<tr><td><font color=\"blue\">" + salesPeople.get(counter).getName() + 
						"</font></td><td align=\"right\"><b>$" + df.format(salesPeople.get(counter).getAmount()) + "</b></td></tr>");
			}
			
			else if ("(adjustment)".equals(salesPeople.get(counter).getName()))
			{
				this.out.println("<tr><td align=\"right\"><font color=\"red\" size=\"2px\"><i>" + 
						salesPeople.get(counter).getName() + "</i></font></td>");
				
				this.out.println("<td align=\"right\"><font color=\"red\" size=\"2px\"><i> $" +
						df.format(salesPeople.get(counter).getAmount()) + "</i></font></td></tr>");
			}
			
			else
			{
				this.out.println("<tr><td><font color=\"green\">" + salesPeople.get(counter).getName() + 
						"</font></td><td align=\"right\"><b>$" + df.format(salesPeople.get(counter).getAmount()) + "</b></td></tr>");
				
				ArrayList <SalesAccount> sAccount = salesPeople.get(counter).getAccounts();
				
//				this.log("ACCOUNT SIZE: " + salesPeople.get(counter).getAccountSize() + " / " + salesPeople.get(counter).getAccounts().size());
				
				for (int index = 0; index < salesPeople.get(counter).getAccountSize(); index++)
				{
					String name = sAccount.get(index).getAccountNames().toString();
					
//					this.log("INDEX: "  + index + " / " + salesPeople.get(counter).getAccountSize());
					
					if (name.startsWith("["))
						name = name.substring(1);
					
					if (name.endsWith("]"))
						name = name.substring(0, name.length() - 1);
					
					this.out.println("<tr><td align=\"right\"><font color=\"gray\" size=\"2px\"><i>" + 
							 name + "</i></font></td>");
					
					ArrayList <Double> amount = sAccount.get(index).getAccountAmount();
					
					this.out.println("<td align=\"right\"><font color=\"gray\" size=\"2px\"><i> $" + 
							df.format(amount.get(0).doubleValue()) + "</i></font></td></tr>");
				}
			}
		}
		
		this.out.println("<tr><td><font color=\"red\"><b>Total</b></font></td><td align=\"right\"><font color=\"red\"><b>$" + df.format(total) + "</font></b></td></tr>");
		this.out.println("</table>");
		this.out.println("</center>");
	}
}