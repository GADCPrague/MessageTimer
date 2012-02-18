package com.devcamp.messagetimer.presenter;

import java.util.Date;
import java.util.List;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.devcamp.messagetimer.MT;
import com.devcamp.messagetimer.MessageEditorActivity;
import com.devcamp.messagetimer.model.Message;
import com.devcamp.messagetimer.model.TemplateText;

public class MessageEditorPresenter
{
	private MessageEditorActivity mActivity = null;
	private Message mCurrentItem = null;
	private Date mLastTime;
	private Date mLastDate;
	
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
		
		mActivity.getWhenTime().setOnFocusChangeListener(new OnFocusChangeListener()
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
		loadTemplates();
	}
	
	private void loadTemplates()
	{
		List<TemplateText> templates = mActivity.getDatabase().getTemplates();
		///TODO set spinner adapter
	}
	
	protected void setMessage(Message msg)
	{
		mCurrentItem = msg;
		
		mActivity.getDateEnabled().setChecked(msg.isTimeEnabled);
		mActivity.getContactName().setText(msg.contact);
		mActivity.getPhoneNumber().setText(msg.phoneNumber);
		mActivity.getMessage().setText(msg.message);
		mActivity.getDateEnabled().setChecked(msg.isTimeEnabled);
		mActivity.getWhenDate().setText(MT.DATEFORMAT.format(msg.when));
		mActivity.getWhenTime().setText(MT.TIMEFORMAT.format(msg.when));
	}
	
	protected Message getMessage()
	{
		if(mCurrentItem == null)
			mCurrentItem = new Message();
		
		mCurrentItem.isTimeEnabled = mActivity.getDateEnabled().isChecked();
		mCurrentItem.contact = mActivity.getContactName().getText().toString();
		mCurrentItem.phoneNumber = mActivity.getPhoneNumber().getText().toString();
		mCurrentItem.message = mActivity.getMessage().getText().toString();
		mCurrentItem.isEnabled = true;///TODO
		mCurrentItem.when = getDateTime();
		
		
		
		return null;
	}
	
	private Date getDateTime()
	{
		return new Date(mLastDate.getYear(), mLastDate.getMonth(), mLastDate.getDay(),mLastTime.getHours(),mLastTime.getMinutes(),mLastTime.getSeconds());
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
