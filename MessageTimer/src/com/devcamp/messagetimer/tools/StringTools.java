package com.devcamp.messagetimer.tools;

import java.util.Random;

public class StringTools
{
	private static final Random r = new Random();
	
	public static String generateRandomString()
	{
		return generateRandomString(5, 10);
	}
	
	public static String generateRandomString(int min, int max)
	{
		int len = min + r.nextInt(max - min + 1);
		StringBuilder sb = new StringBuilder();
		for(int i = 0;i<len;i++)
		{
			sb.append(generateRandomChar());
		}
		return sb.toString();
	}
	
	public static char generateRandomChar(){
		return (char)('a' + r.nextInt((int)('z' - 'a')));
	}
	
	public static String generateRandomNumericString(int min, int max)
	{
		StringBuilder sb = new StringBuilder();
		int len = min + r.nextInt(max - min + 1);
		for(int i = 0;i<len;i++)
			sb.append(r.nextInt(10));
		return sb.toString();
	}
}
