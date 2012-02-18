package com.devcamp.messagetimer.presenter;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.devcamp.messagetimer.MessageEditorActivity;

public class MessageEditorPresenter
{
	private MessageEditorActivity mActivity = null;
	
	public MessageEditorPresenter(MessageEditorActivity activity)
	{
		mActivity = activity;
		bind();
	}
	
	private void bind()
	{
		mActivity.getAddTemplate().setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				onAddTemplateClick();
			}
		});
		
		mActivity.getSaveMessage().setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				onSaveMessageClick();
			}
		});
		
		mActivity.getWhenDate().setOnFocusChangeListener(new OnFocusChangeListener()
		{
			
			@Override
			public void onFocusChange(View v, boolean hasFocus)
			{
				onDateGetFocus();
			}
		});
		
		mActivity.getWhenHour().setOnFocusChangeListener(new OnFocusChangeListener()
		{
			
			@Override
			public void onFocusChange(View v, boolean hasFocus)
			{
				onTimeGetFocus();
			}
		});
		
		mActivity.getTemplate().setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0){}
		});
	}
	
	public void onAddTemplateClick()
	{
		
	}
	
	public void onSaveMessageClick()
	{
		
	}
	
	public void onDateGetFocus()
	{
		
	}
	
	public void onTimeGetFocus()
	{
		
	}
	
	public void onSelectedTemplate()
	{
		
	}
}
