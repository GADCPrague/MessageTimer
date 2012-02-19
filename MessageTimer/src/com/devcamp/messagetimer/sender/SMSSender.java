package com.devcamp.messagetimer.sender;

import com.devcamp.messagetimer.tools.PropertyProvider;

import android.content.Context;
import android.util.Log;

public class SMSSender extends Sender
{
	
	public SMSSender(Context context)
	{
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void sendMessage(String to, String message)
	{
		Log.v("SMSSender","sendMessage");
	}

}
