package com.devcamp.messagetimer.sender;

import com.devcamp.messagetimer.MessageTimerApplication;
import com.devcamp.messagetimer.tools.PropertyProvider;

import android.content.Context;

public abstract class Sender
{
	protected Context mContext = null;
	private PropertyProvider mPropertyProvider = null;
	
	public Sender(Context context)
	{
		mContext = context;
		mPropertyProvider = ((MessageTimerApplication)mContext.getApplicationContext()).getPropertyProvider();
	}
	
	/***
	 * Returns property, if value is not found null is returned
	 * @param name
	 * @return
	 */
	public String getStringProperty(String name)
	{
		return mPropertyProvider.getString(name, null);
	}
	
	public abstract void sendMessage(String to, String message);
}
