package com.devcamp.messagetimer.presenter;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.devcamp.messagetimer.MT;
import com.devcamp.messagetimer.MessageEditorActivity;
import com.devcamp.messagetimer.MessageListActivity;
import com.devcamp.messagetimer.model.Message;

public class MessageListPresenter {
	private MessageListActivity mActivity = null;
	
	public MessageListPresenter(MessageListActivity activity)
	{
		mActivity = activity;
		bind();
	}
	
	private void bind(){
		mActivity.GetList().setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3){
				onMessageClick(((Message)arg0.getItemAtPosition(arg2)).id);
			}
		});
		mActivity.GetAddMessage().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onMessageAddClick();
			}
		});
	}
	
	private void onMessageClick(long id) {
		Intent myIntent = new Intent(mActivity, MessageEditorActivity.class);
		myIntent.putExtra(MT.MESSAGE_ID, id);
		mActivity.startActivity(myIntent);
	}
	
	private void onMessageAddClick() {
		Intent myIntent = new Intent(mActivity, MessageEditorActivity.class);
		mActivity.startActivity(myIntent);
	}
}
