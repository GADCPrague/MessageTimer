package com.devcamp.messagetimer.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.devcamp.messagetimer.model.Message;
import com.devcamp.messagetimer.tools.StringTools;

public class DataGenerator
{
	private static Random r = new Random();
	public static List<Message> getRandomMessages(int howMany)
	{
		List<Message> data = new ArrayList<Message>();
		for(int i = 0;i<howMany;i++)
		{
			data.add(getRandomMessage());
		}
		return data;
	}
	public static Message getRandomMessage()
	{
		Message m = new Message();
		m.isEnabled = r.nextBoolean();
		m.message = StringTools.generateRandomString(50, 100);
		m.phoneNumber = "+420" + StringTools.generateRandomNumericString(6, 9);
		m.when = generateRandomDate();
		return m;
	}
	
	public static Date generateRandomDate()
	{
		return new Date(50 + r.nextInt(60),r.nextInt(12),r.nextInt(28),r.nextInt(24), r.nextInt(59),r.nextInt(59));
	}
}
