package com.devcamp.messagetimer;

import java.util.ArrayList;
import java.util.List;

import com.devcamp.messagetimer.model.Message;


import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MessageList extends ListActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	 super.onCreate(savedInstanceState);
         setContentView(R.layout.message_list);
         
         List<Message> items =  new ArrayList<Message>();
         Message item = new Message();
         item.phoneNumber= "+420 724 035 206";
         item.message= "sorry, prijdu pozdeji...";
         items.add(item);
         item.phoneNumber= "+420 724 035 206";
         item.message= "sorry, prijdu pozdeji...";
         items.add(item);
         item.phoneNumber= "+420 724 035 206";
         item.message= "sorry, prijdu pozdeji...";
         items.add(item);
         item.phoneNumber= "+420 724 035 206";
         item.message= "sorry, prijdu pozdeji...";
         items.add(item);
         item.phoneNumber= "+420 724 035 206";
         item.message= "sorry, prijdu pozdeji...";
         items.add(item);
         item.phoneNumber= "+420 724 035 206";
         item.message= "sorry, prijdu pozdeji...";
         items.add(item);
         item.phoneNumber= "+420 724 035 206";
         item.message= "sorry, prijdu pozdeji...";
         items.add(item);
         item.phoneNumber= "+420 724 035 206";
         item.message= "sorry, prijdu pozdeji...";
         items.add(item);
         item.phoneNumber= "+420 724 035 206";
         item.message= "sorry, prijdu pozdeji...";
         items.add(item);
         item.phoneNumber= "+420 724 035 206";
         item.message= "sorry, prijdu pozdeji...";
         items.add(item);
         item.phoneNumber= "+420 724 035 206";
         item.message= "sorry, prijdu pozdeji...";
         items.add(item);
         item.phoneNumber= "+420 724 035 206";
         item.message= "sorry, prijdu pozdeji...";
         items.add(item);
         
         ListView l = (ListView)this.findViewById(android.R.id.list);
         l.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				arg1.setSelected(true);				
			}});
         
         this.setListAdapter(new MessageAdapter(this,R.layout.message_list_item,(ArrayList<Message>)items));
    }
}
