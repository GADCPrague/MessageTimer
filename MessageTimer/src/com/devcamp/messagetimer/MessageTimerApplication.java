package com.devcamp.messagetimer;

import com.devcamp.messagetimer.tools.Database;
import com.devcamp.messagetimer.tools.PropertyProvider;

import android.app.Application;

public class MessageTimerApplication extends Application
{
	private Database mDatabase = null;
	private PropertyProvider mPropertyProvider = null;
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		
		mDatabase = new Database(this);
		mPropertyProvider = PropertyProvider.getProvider(this);
	}
	
	public Database getDatabase()
	{
		return mDatabase;
	}
	
	public PropertyProvider getPropertyProvider()
	{
		return mPropertyProvider;
	}
}
