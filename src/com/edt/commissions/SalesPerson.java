package com.edt.commissions;

import java.util.ArrayList;

public class SalesPerson
{
	private String name = null;
	private double amount = 0;
	
	private ArrayList <SalesAccount> accounts = null;
	
	public ArrayList<SalesAccount> getAccounts()
	{
		return this.accounts;
	}
	
	public boolean isAccountNameInList(String salesName, String suppCust)
	{
		boolean found = false;
		
//		if this sale name has been set, compare it to the name passed in
		if (null != this.name)
			found = this.name.equals(salesName);
		
//		if this sale name is the same as the name passed in
		if (found)
		{
//			if the accounts object does not exist, return false
			if ((null == this.accounts) || (0 == this.accounts.size()))
				return false;
			
			int index = 0;
			
//			parse thru the list of accounts
			for (index = 0; index < this.accounts.size(); index++)
			{
//				for each account, get the object
				SalesAccount accountClass = this.accounts.get(index);
//				for each account object, get a list of account names
				ArrayList <String> names = accountClass.getAccountNames();
				
				found = false;
				
//				for each name, see if we already stored it; if we find it, quit this loop and return true
				for (int count = 0; (count < names.size()) && (!found); count++)
					found = suppCust.equals(names.get(count));
			}
		}
		
		return found;
	}

	public boolean setAccountAmount(SalesAccount account, String name, double amount)
	{
		boolean setAmount = false;
		
		if ((null != account) && (null != this.accounts))
		{
			if ((!this.accounts.isEmpty()) && (this.accounts.contains(account)))
			{
				int index = this.accounts.lastIndexOf(account);
				this.accounts.get(index).setAccount(name, amount);
				setAmount = true;
			}
		}
		
		return setAmount;
	}
	
	public void setAccountAmount(String account, double amount)
	{
		if (null != account)
		{
			SalesAccount accountClass = null;
			
			boolean found = false;
			
//			parse thru this accounts object to find the account name
			for (int index = 0; (index < this.accounts.size()) && (!found); index++)
			{
				accountClass = this.accounts.get(index);
				
				ArrayList <String> names = accountClass.getAccountNames();
				
				for (int count = 0; (count < names.size()) && (!found); count++)
					found = account.equals(names.get(count));
				
				if (found)
					accountClass.setAccount(account, amount);
			}
		}
	}
	
	public void setAccounts(String account, double amount)
	{
		SalesAccount accountClass = null;
		
		boolean found = false;
		
//		if this accounts object hasn't been set yet
		if (null == this.accounts)
		{
			this.accounts = new ArrayList<SalesAccount>();
			
			accountClass = new SalesAccount();
			accountClass.setAccount(account, amount);
		}
//		else this account object has been set
		else
		{
			int index = 0;
			
//			parse thru this accounts object to find the account name
			for (index = 0; index < this.accounts.size(); index++)
			{
				accountClass = this.accounts.get(index);
				
				ArrayList <String> names = accountClass.getAccountNames();
				
				for (int count = 0; (count < names.size()) && (!found); count++)
					found = account.equals(names.get(count));
			}
			
//			create a new amount object
			if ((0 == index) || (!found))
				accountClass = new SalesAccount();
			
			accountClass.setAccount(account, amount);
		}
		
		this.accounts.add(accountClass);
	}
	
	public int getAccountSize()
	{
		return (null != this.accounts) ? this.accounts.size() : 0;
	}

	public SalesPerson(String name)
	{
		super();
		
		this.name = name;
		this.accounts = new ArrayList<SalesAccount>();
	}

	public String getName()
	{
		return this.name;
	}

	public boolean compare(String newName)
	{
		return (null == this.name) ? false : (newName == this.name);
	}
	
	public double getActualAmount()
	{
		return this.amount;
	}
	
	public double getAmount()
	{
		return (this.amount != 0) ? this.amount : 0;
	}

	public void setAmount(double amount)
	{
//		PrintOut.log("SalesPerson.setAmount ADDING " + amount + " TO " + this.amount + " TO GET " + (amount + this.amount));
		this.amount += amount;
	}
}