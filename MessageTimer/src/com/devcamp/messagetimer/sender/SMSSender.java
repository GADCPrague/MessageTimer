package com.devcamp.messagetimer.sender;

import com.devcamp.messagetimer.tools.PropertyProvider;

import android.content.Context;
import android.telephony.SmsManager;
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
		SmsManager sm = SmsManager.getDefault();
//		sm.sendTextMessage(to, null, message, null, null);
		Log.v("SMSSender","sendMessage");
	}

}
