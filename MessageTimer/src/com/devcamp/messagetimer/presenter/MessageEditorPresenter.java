package com.devcamp.messagetimer.presenter;

import java.security.InvalidParameterException;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.text.method.CharacterPickerDialog;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SpinnerAdapter;
import android.widget.TimePicker;
import android.widget.AdapterView.OnItemSelectedListener;

import com.devcamp.messagetimer.MT;
import com.devcamp.messagetimer.MessageEditorActivity;
import com.devcamp.messagetimer.R;
import com.devcamp.messagetimer.model.Message;
import com.devcamp.messagetimer.model.TemplateText;
import com.devcamp.messagetimer.sender.GTalkSender;
import com.devcamp.messagetimer.sender.MailSender;
import com.devcamp.messagetimer.sender.SMSSender;
import com.devcamp.messagetimer.tools.Database;
import com.devcamp.messagetimer.tools.MessageAlarmManager;
import com.devcamp.messagetimer.tools.MobileSettingsProvider;

public class MessageEditorPresenter
{
	private MessageEditorActivity mActivity = null;
	private Message mCurrentItem = null;
	private Date mLastTime;
	private Date mLastDate;
	private Database mDatabase = null;
	private String[] mServiceValues = null;
	private MessageAlarmManager mAlarmManager = null;
	
	public MessageEditorPresenter(MessageEditorActivity activity)
	{
		try
		{
			mActivity = activity;
			mDatabase = mActivity.getDatabase();
			init();
			bind();
		}
		catch(Exception e)
		{
			mActivity.showMessage(e);
			mActivity.finish();
		}
	}
	
	private void init()
	{
		mServiceValues = mActivity.getResources().getStringArray(R.array.servicesValues);
		mAlarmManager = new MessageAlarmManager(mActivity);
//		if(mActivity.getIntent().hasExtra(MT.MESSAGE_ID))
//		{
//			Long id = mActivity.getIntent().getExtras().getLong(MT.MESSAGE_ID);
//			setMessage(mDatabase.getMessages(id).get(0));
//		}
		
			Long id = (long) 1;
			setMessage(mDatabase.getMessages(id).get(0));
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
				if(hasFocus)
					onDateGetFocus();
			}
		});
		
		mActivity.getWhenTime().setOnFocusChangeListener(new OnFocusChangeListener()
		{
			
			@Override
			public void onFocusChange(View v, boolean hasFocus)
			{
				if(hasFocus)
					onTimeGetFocus();
			}
		});
		
		mActivity.getTemplate().setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				ArrayAdapter<TemplateText> adapter = (ArrayAdapter<TemplateText>) arg0.getAdapter();
				TemplateText item = adapter.getItem(arg2);
				onSelectedTemplate(item);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0){}
		});
		
		mActivity.getAddContact().setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				onAddingContact();
			}
		});
		
		loadTemplates();
	}
	
	private void loadTemplates()
	{
		List<TemplateText> templates = mDatabase.getTemplates();
		templates.add(0, new TemplateText());
		ArrayAdapter<TemplateText> adapter = new ArrayAdapter<TemplateText>(mActivity, android.R.layout.simple_spinner_item,templates);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mActivity.getTemplate().setAdapter(adapter);
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
		
		for(int i = 0;i<mServiceValues.length;i++)
		{
			String v = mServiceValues[i];
			if(msg.service.equals(v))
			{
				mActivity.getService().setSelection(i);
				break;
			}
		}
		
		mLastDate = msg.when;
		mLastTime = msg.when;
	}
	
	protected Message getMessage()
	{
		if(mCurrentItem == null)
			mCurrentItem = new Message();
		
		mCurrentItem.service = mServiceValues[mActivity.getService().getSelectedItemPosition()];
		mCurrentItem.isTimeEnabled = mActivity.getDateEnabled().isChecked();
		mCurrentItem.contact = mActivity.getContactName().getText().toString();
		mCurrentItem.phoneNumber = mActivity.getPhoneNumber().getText().toString();
		mCurrentItem.message = mActivity.getMessage().getText().toString();
		mCurrentItem.isEnabled = true;///TODO
		mCurrentItem.when = getDateTime();
		
		return mCurrentItem;
	}
	
	private Date getDateTime()
	{
		if(mLastDate == null) throw new InvalidParameterException("DATE");
		if(mLastTime == null) throw new InvalidParameterException("TIME");
		return new Date(mLastDate.getYear(), mLastDate.getMonth(), mLastDate.getDate(),mLastTime.getHours(),mLastTime.getMinutes(),0);
	}
	
	public void onAddTemplateClick()
	{
		AlertDialog.Builder b = new Builder(mActivity);
		LinearLayout ll = new LinearLayout(mActivity);
		ll.setPadding(5, 2, 5, 2);
		
		final EditText et = new EditText(mActivity);
		et.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		ll.addView(et);
		
		b.setPositiveButton(R.string.txtOK, new Dialog.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
//				mActivity.registerReceiver(new TestRec(), new IntentFilter());
//				Intent i = new Intent(mActivity, TestRec.class);
//				mActivity.sendBroadcast(i);
				
//				
//				String t = et.getText().toString();
//				TemplateText tt = new TemplateText();
//				tt.name = t;
//				tt.value = mActivity.getMessage().getText().toString();
//				onAddTemplate(tt);
			}
		});
		b.setTitle(R.string.txtTemplateName);
		b.setView(ll);
		b.create().show();
	}
	
	public void onAddTemplate(TemplateText tt)
	{
		
		
		
		try
		{
			if(tt.name == null || tt.name.trim().length() == 0)
				throw new Exception("MISSING NAME");
			if(tt.value == null || tt.value.trim().length() == 0)
				throw new Exception("MISSING VALUE");
			
			//save to DB
			mDatabase.addTemplate(tt);
			mActivity.showDialog(R.string.txtSaved);
			
			loadTemplates();///TODO BETTER
			
		}
		catch(Exception e)
		{
			mActivity.showMessage(e.getMessage());
		}
	}
	
	public void onSaveMessageClick()
	{
		try
		{
			Message m = getMessage();
			m.check();
			
			if(m.id != 0)
				mDatabase.updateMessage(m);
			else
				mDatabase.addMessage(m);
			
			mAlarmManager.activateOrUpdateAlarm(m);
			mActivity.showMessage(R.string.txtSaved);
			mActivity.finish();
		}
		catch(InvalidParameterException ipe)
		{
			mActivity.showMessage("MISSING OR WRONG VALUE: " + ipe.getMessage());
		}
		catch(Exception e)
		{
			mActivity.showMessage(e.getMessage());
		}
	}
	
	public void onDateGetFocus()
	{
		DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener()
		{
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
			{
				if(mLastDate == null)
					mLastDate = new Date();
				mLastDate.setYear(year - 1900);
				mLastDate.setMonth(monthOfYear);
				mLastDate.setDate(dayOfMonth);
				mActivity.getWhenDate().setText(MT.DATEFORMAT.format(mLastDate));
			}
		};
		
		Date now = new Date(System.currentTimeMillis());
		int years = (mLastTime == null) ? now.getYear() : mLastTime.getYear();
		years += 1900;//OMG!
		int month = (mLastTime == null) ? now.getMonth() : mLastTime.getMonth();
//		month -= 1;//OMG FUCK!
		int days = (mLastTime == null) ? now.getDate() : mLastTime.getDate();
		
		DatePickerDialog dpd = new DatePickerDialog(mActivity, callback, years, month, days);
		dpd.show();
	}
	
	public void onTimeGetFocus()
	{
		TimePickerDialog.OnTimeSetListener callback = new TimePickerDialog.OnTimeSetListener()
		{
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute)
			{
				if(mLastTime == null)
					mLastTime = new Date();
				mLastTime.setHours(hourOfDay);
				mLastTime.setMinutes(minute);
				mActivity.getWhenTime().setText(MT.TIMEFORMAT.format(mLastTime));
			}
		};
		Date now = new Date(System.currentTimeMillis());
		int hour = (mLastTime == null) ? now.getHours() : mLastTime.getHours();
		int minute = (mLastTime == null) ? now.getMinutes() : mLastTime.getMinutes();
		
		TimePickerDialog tp = new TimePickerDialog(mActivity, callback, hour, minute, 
												   MobileSettingsProvider.getTimeFormat(mActivity) == MobileSettingsProvider.TIME_FORMAT_24H);
		tp.show();
	}
	
	public void onSelectedTemplate(TemplateText tt)
	{
		if(tt != null && tt.name != null)//name can be null if first null 
			mActivity.getMessage().setText(tt.value);
	}
	
	public void onAddingContact()
	{
		Uri uri = null;
		String msgSender = mServiceValues[mActivity.getService().getSelectedItemPosition()];
		if(msgSender.equals(SMSSender.class.getName()))
			uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
//		else if (msgSender.contains(MailSender.class.getName()))
//			uri = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
//		else if (msgSender.contains(MailSender.class.getName()))
//			uri = ContactsContract.Contacts.CONTENT_URI;
		
		if(uri != null)
		{
			Intent intent = new Intent(Intent.ACTION_PICK, uri);
			mActivity.startActivityForResult(intent, MT.CONTACT_PICK_REQUEST);
		}
		else
		{
			mActivity.showMessage(R.string.txtNoSelector);
		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		try
		{
			if(resultCode == Activity.RESULT_OK)
			{
				switch(requestCode)
				{
					case MT.CONTACT_PICK_REQUEST: handleSelectedContact(data); break;
				}
			}
		}
		catch(Exception e)
		{
			mActivity.showMessage(e);
		}
	}
	
	
	private void handleSelectedContact(Intent data)
	{
		String msgSender = mServiceValues[mActivity.getService().getSelectedItemPosition()];
		if(msgSender.equals(SMSSender.class.getName()))
			handleSelectContactPhone(data);
		else if(msgSender.equals(MailSender.class.getName()) || msgSender.equals(GTalkSender.class.getName()))
			handleSelectContactEmail(data);
		else
		{
			
		}
		
	}
	
	protected void handleSelectContactPhone(Intent data)
	{
		Uri contactData = data.getData();
        Cursor c =  mActivity.managedQuery(contactData, null, null, null, null);
        if (c.moveToFirst()) 
        {
        	String name = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
        	String value = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        	onSelectContact(name,value);
        }
        c.close();
	}
	
	protected void handleSelectContactEmail(Intent data)
	{
		Uri contactData = data.getData();
        Cursor c =  mActivity.managedQuery(contactData, null, null, null, null);
        if (c.moveToFirst()) 
        {
        	String name = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Email.DISPLAY_NAME));
        	for(int i = 0;i<c.getColumnCount();i++)
        	{
        		Log.d("",1 + " " + c.getColumnName(i) + " " + c.getString(i));
        	}
        	onSelectContact(name,name);
        }
        c.close();
	}
	
	protected void onSelectContact(String userName, String value)
	{
		mActivity.getContactName().setText(userName);
		mActivity.getPhoneNumber().setText(value);
	}
}
