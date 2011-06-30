package com.Smart_task1;

import android.content.Context;

public class Task {
	
	int _id;
	String _name;
	String _desp;
	String _type;
	String _priority;
	String _cat;
	String _sdate;
	String _ddate;
	
	public Task(String Name,String desp,String type,String priority,String cat, String sdate,String ddate)
	{
		
		this._name=Name;
		this._desp=desp;
		this._type=type;
		this._priority=priority;
		this. _cat=cat;
		this._sdate=sdate;
		this. _ddate=ddate;
	}
	
	
	
	public int getID()
	{
		return this._id;
	}
	public void SetID(int ID)
	{
		this._id=ID;
	}
	
	public String getName()
	{
		return this._name;
	}
	
	public String desp()
	{
		return this._desp;
	}
	public String type()
	{
		return this._type;
	}
	public String priority()
	{
		return this._priority;
	}
	public String cat()
	{
		return this._cat;
	}
	public String sdate()
	{
		return this._sdate;
	}
	public String ddate()
	{
		return this._ddate;
	}
	public void setName(String Name)
	{
		this._name=Name;
	}
	public void setdesp(String desp)
	{
		this._desp= desp;
	}
	public void settype(String type)
	{
		this._type= type;
	}
	public void setpriority(String priority)
	{
		this._priority = priority ;
	}
	public void setcat(String cat)
	{
		this._cat= cat;
	}
	public void setsdate(String sdate)
	{
		this._sdate= sdate;
	}
	public void ddate(String ddate)
	{
		this._ddate= ddate;
	}
	
	
	
	
}
