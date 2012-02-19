package com.devcamp.messagetimer.model;

public class TemplateText
{
	public long id;
	public String name;
	public String value;
	
	@Override
	public String toString()
	{
		if(name == null)
			return "";
		return name;
	}
}
