package com.devcamp.messagetimer;

import com.devcamp.messagetimer.tools.Database;

import android.app.Application;

public class MessageTimerApplication extends Application
{
	private Database mDatabase = null;
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		
		mDatabase = new Database(this);
	}
	
	public Database getDatabase()
	{
		return mDatabase;
	}
}
