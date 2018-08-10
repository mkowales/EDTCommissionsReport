package com.edt.commissions;

import java.util.ArrayList;

public class SalesAccount
{
	private ArrayList <String> accountNames = null;
	private ArrayList <Double> accountAmount = null;
	
	public SalesAccount()
	{
		super();
	}
	
	public void setAccount(String account, double amount)
	{
		int counter = 0;
		
		boolean found = false;
		
		if (null == this.accountNames)
			this.accountNames = new ArrayList<String>();
		else
		{
// 			look for name
			for (counter = 0; ((counter < this.accountNames.size()) && (!found)); counter++)
				found = account.equals(this.accountNames.get(counter));
		}

// 		if the name doesn't exist, add it
		if (!found)
			this.accountNames.add(account);
		
		Double newAmount = (double)amount;
		
		if (null == this.accountAmount)
		{
			this.accountAmount = new ArrayList<Double>();
			this.accountAmount.add(newAmount);
		}
		
		else if (!found)
			this.accountAmount.add(newAmount);
		
		else
		{
			counter--;
			
			// increase the amount & add it back in the list
			newAmount = this.accountAmount.get(counter) + amount;
			this.accountAmount.set(counter, newAmount);
		}
	}
	
	public ArrayList <String> getAccountNames()
	{
		return this.accountNames;
	}

	public ArrayList <Double> getAccountAmount()
	{
		return this.accountAmount;
	}
}