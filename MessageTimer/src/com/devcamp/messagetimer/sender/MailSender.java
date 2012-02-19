package com.devcamp.messagetimer.sender;

import android.content.Context;
import android.util.Log;

public class MailSender extends Sender
{

	public MailSender(Context context)
	{
		super(context);
	}

	@Override
	public void sendMessage(String to, String message)
	{
		Log.v("SMSSender","sendMessage");
	}

}
