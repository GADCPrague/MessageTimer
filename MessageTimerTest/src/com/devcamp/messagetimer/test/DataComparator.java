package com.devcamp.messagetimer.test;

import java.util.Date;
import java.util.List;

import com.devcamp.messagetimer.model.Message;
import com.devcamp.messagetimer.model.TemplateText;

import junit.framework.TestCase;

public class DataComparator extends TestCase
{
	public static void assertEquals(Message m1, Message m2, boolean ids)
	{
		if(ids)
			assertEquals(m1.id,m2.id);
		
		assertEquals(m1.service,m2.service);
		assertEquals(m1.isEnabled,m2.isEnabled);
		assertEquals(m1.contact, m2.contact);
		assertEquals(m1.message, m2.message);
		assertEquals(m1.phoneNumber, m2.phoneNumber);
		assertEquals(m1.isTimeEnabled, m2.isTimeEnabled);
		assertEquals(m1.when, m2.when,false);
	}
	
	public static void assertEqualsTemplateTexts(List<TemplateText> m1, List<TemplateText> m2, boolean ids)
	{
		assertEquals(m1.size(),m2.size());
		for(int i = 0;i<m1.size();i++)
			assertEquals(m1.get(i),m2.get(i),ids);
	}
	
	public static void assertEquals(TemplateText m1, TemplateText m2, boolean ids)
	{
		if(ids)
			assertEquals(m1.id,m2.id);
		
		assertEquals(m1.name,m2.name);
		assertEquals(m1.value,m2.value);
	}
	
	public static void assertEqualsMessages(List<Message> m1, List<Message> m2, boolean ids)
	{
		assertEquals(m1.size(),m2.size());
		for(int i = 0;i<m1.size();i++)
			assertEquals(m1.get(i),m2.get(i),ids);
	}
	
	/**
	 * 
	 * @param d1
	 * @param d2
	 * @param exact if true its compared by exactly {@link Date#getTime()}, <br>
	 * otherwise is compared by year, month, day, hours, minutes, seconds
	 */
	public static void assertEquals(Date d1, Date d2, boolean exact)
	{
		if(exact)
			assertEquals(d1.getTime(),d2.getTime());
		else
		{
			assertEquals(d1.getYear(),d2.getYear());
			assertEquals(d1.getMonth(),d2.getMonth());
			assertEquals(d1.getDay(),d2.getDay());
			assertEquals(d1.getHours(),d2.getHours());
			assertEquals(d1.getMinutes(),d2.getMinutes());
			assertEquals(d1.getSeconds(),d2.getSeconds());
		}
	}
}
