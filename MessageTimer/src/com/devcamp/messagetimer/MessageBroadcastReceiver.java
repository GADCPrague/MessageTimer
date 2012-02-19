package com.devcamp.messagetimer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MessageBroadcastReceiver extends BroadcastReceiver
{

	@Override
	public void onReceive(final Context context, final Intent intent)
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				onReceiveImpl(context,intent);
			}
		},"MessageReceiver.onReceive").start();
	}
	
	private void onReceiveImpl(Context context, Intent intent)
	{
		
	}
	
//	private void notifyUserAboutSend()
//	{
//		
//	}
}
