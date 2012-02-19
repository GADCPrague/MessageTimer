package com.devcamp.messagetimer.model;

import java.security.InvalidParameterException;
import java.util.Date;

public class Message
{
	public long id;
	public String phoneNumber;
	public String message;
	public Date when;
	public String contact;
	public boolean isTimeEnabled;
	public boolean isEnabled;
	public String service;
	
	public void check() throws InvalidParameterException
	{
		if(phoneNumber.trim().length() == 0) throw new InvalidParameterException("phoneNumber");
		if(message.trim().length() == 0) throw new InvalidParameterException("message");
		if(when == null) throw new InvalidParameterException("when");
		if(contact.trim().length() == 0) throw new InvalidParameterException("contact");
		if(service.trim().length() == 0) throw new InvalidParameterException("service");
	}
}
