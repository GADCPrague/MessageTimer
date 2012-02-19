package com.devcamp.messagetimer.sender;

import com.devcamp.messagetimer.R;

import android.content.Context;
import android.util.Log;

public class GTalkSender extends Sender
{

	public GTalkSender(Context context)
	{
		super(context);
	}

	@Override
	public void sendMessage(String to, String message)
	{
		Log.v("SMSSender","sendMessage");
	}
	
	private String getUserName()
	{
		return getStringProperty(R.string.PROPERTY_GTALK_LOGIN);
	}
	
	private String getPasword()
	{
		return getStringProperty(R.string.PROPERTY_GTALK_PASSWORD);
	}

}
