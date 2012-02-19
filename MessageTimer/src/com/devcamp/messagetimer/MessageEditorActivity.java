package com.devcamp.messagetimer;

import com.devcamp.messagetimer.presenter.MessageEditorPresenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MessageEditorActivity extends BaseActivity
{
	private Spinner mService = null;
	private View mContentView = null;
	private TextView mContactName = null;
	private Button mAddContact = null;
	private EditText mPhoneNumber = null;
	private Spinner mTemplate = null;
	private EditText mMessage = null;
	
	private CheckBox mDateEnabled = null;
	private EditText mWhenHour = null;
	private EditText mWhenDate = null;
	private Button mAddTemplate = null;
	private Button mSaveMessage = null;
	
	private MessageEditorPresenter mPresenter = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		init();
		mPresenter = new MessageEditorPresenter(this);
	}
	
	private void init()
	{
		mContentView = View.inflate(this, R.layout.message_editor, null);
		setContentView(mContentView);
		mService = (Spinner) findViewById(R.id.spService);
		mContactName = (TextView) findViewById(R.id.tvContactName);
		mAddContact = (Button)findViewById(R.id.btnAddContact);
		mPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
		mTemplate = (Spinner) findViewById(R.id.spTemplates);
		mMessage = (EditText) findViewById(R.id.etMessage);
		mDateEnabled = (CheckBox) findViewById(R.id.chkTime);
		mWhenDate = (EditText) findViewById(R.id.etDate);
		mWhenHour = (EditText) findViewById(R.id.etTime);
		mAddTemplate = (Button) findViewById(R.id.btnAddTemplate);
		mSaveMessage = (Button) findViewById(R.id.btnSaveMessage);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		mPresenter.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected View getContentView()
	{
		return mContentView;
	}
	
	public TextView getContactName()
	{
		return mContactName;
	}

	public EditText getPhoneNumber()
	{
		return mPhoneNumber;
	}

	public Spinner getTemplate()
	{
		return mTemplate;
	}

	public EditText getMessage()
	{
		return mMessage;
	}

	public CheckBox getDateEnabled()
	{
		return mDateEnabled;
	}

	public EditText getWhenTime()
	{
		return mWhenHour;
	}

	public EditText getWhenDate()
	{
		return mWhenDate;
	}

	public Button getAddTemplate()
	{
		return mAddTemplate;
	}

	public Button getSaveMessage()
	{
		return mSaveMessage;
	}
	
	public Spinner getService()
	{
		return mService;
		
	}

	public Button getAddContact()
	{
		return mAddContact;
	}
}
