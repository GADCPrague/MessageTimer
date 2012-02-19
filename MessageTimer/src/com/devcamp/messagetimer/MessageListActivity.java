package com.devcamp.messagetimer;

import java.util.ArrayList;

import com.devcamp.messagetimer.model.Message;
import com.devcamp.messagetimer.presenter.MessageListPresenter;
import com.devcamp.messagetimer.tools.Database;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

public class MessageListActivity extends BaseActivity {
	
	private Button mAddMessage = null;
	private ListView mList = null;
	private View mContentView = null;
	private Database mDb = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.message_list);
        init();
 		new MessageListPresenter(this);
 		
 		Intent i = new Intent(this, MessageEditorActivity.class);
 		startActivity(i);
    }
    
    private void init() {
     
        mAddMessage = (Button) findViewById(R.id.btn_message_add);
        mContentView = View.inflate(this, R.layout.message_editor, null);
        mList =  (ListView) findViewById(android.R.id.list);
        mDb = this.getDatabase();
        mList.setAdapter(new MessageAdapter(this,R.layout.message_list_item,(ArrayList<Message>)mDb.getMessages()));
	}
    
    public Button GetAddMessage(){
    	return mAddMessage;
    }
    
    public ListView GetList(){
    	return mList;
    }
    
	@Override
	protected View getContentView() {
		return mContentView;
	}
	
	@Override
	protected void onResume() {
		mList.setAdapter(new MessageAdapter(this,R.layout.message_list_item,(ArrayList<Message>)mDb.getMessages()));
		super.onResume();
	} 
	
}
