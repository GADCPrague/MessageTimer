package com.devcamp.messagetimer.tools;

import com.devcamp.messagetimer.MT;
import com.devcamp.messagetimer.MessageBroadcastReceiver;
import com.devcamp.messagetimer.model.Message;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MessageAlarmManager
{
	private Context mContext = null;
	private final AlarmManager mAlarmManager;
	
	public MessageAlarmManager(Context c)
	{
		mContext = c;
		mAlarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
	}
	
	public void activateOrUpdateAlarm(Message m)
	{		 
		 if(m.id == 0)
			 throw new IllegalArgumentException("MISSING ID!");
		 Intent intent = getIntent(m);
		 PendingIntent sender = PendingIntent.getBroadcast(mContext, MT.SEND_MESSAGE_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);		 	 
		 mAlarmManager.set(AlarmManager.RTC_WAKEUP, m.when.getTime(), sender);
		 Log.v("AlarmManager.activateOrUpdateAlarm", String.format("AlarmActivation ID=%s, Time:%s:%s C:%s",m.id,m.when.toLocaleString()));
	}
			
	public void deactivateAlarm(Message m)
	{
		if(m.id == 0)
			 throw new IllegalArgumentException("MISSING ID!");
		 Intent intent = getIntent(m);		 		
		 PendingIntent sender = PendingIntent.getBroadcast(mContext, MT.SEND_MESSAGE_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);		 	 
		 mAlarmManager.cancel(sender);
		 Log.v("AlarmManager.deactivateAlarm", String.format("MessageDeactivation ID=%s",m.id));
	}
	
	private Intent getIntent(Message m)
	{
		Intent intent = new Intent(mContext, MessageBroadcastReceiver.class);
		intent.setAction(MT.SEND_MESSAGE_ACTION);
		//intent.setType(String.format("ID=%s", alarm.ID));
		intent.putExtra(MT.MESSAGE_ID, m.id);
		return intent;
	}
}
