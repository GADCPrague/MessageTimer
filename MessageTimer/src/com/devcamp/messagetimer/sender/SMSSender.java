package com.devcamp.messagetimer.sender;

import com.devcamp.messagetimer.tools.PropertyProvider;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class SMSSender extends Sender
{
	
	public SMSSender(Context context)
	{
		super(context);
	}

	@Override
	public void sendMessage(String to, String message)
	{
//		Toast.makeText(mContext.getApplicationContext(), "SMS Send to " + to, Toast.LENGTH_LONG).show();
		Log.v("SMSSender","sendMessage");
	}

}
