package com.devcamp.messagetimer.sender;

import android.content.Context;

public abstract class Sender
{
	protected Context mContext = null;
	
	public Sender(Context context)
	{
		mContext = context;
	}
	
	public abstract void sendMessage(String to, String message);
}
