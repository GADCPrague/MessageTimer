package com.devcamp.messagetimer;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

public abstract class BaseActivity extends Activity
{
	
	public void showMessage(final String msg)
	{
		getContentView().post(new Runnable()
		{
			@Override
			public void run()
			{
				Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_LONG).show();
			}
		});
	}
	
	public void showMessage(final int msg)
	{
		getContentView().post(new Runnable()
		{
			@Override
			public void run()
			{
				Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_LONG).show();
			}
		});
	}
	
	public void showMessage(final Throwable e)
	{
		getContentView().post(new Runnable()
		{
			@Override
			public void run()
			{
				Toast.makeText(BaseActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
			}
		});
	}
	
	protected abstract View getContentView();
}
