package com.devcamp.messagetimer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import com.devcamp.messagetimer.model.Message;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MessageAdapter extends ArrayAdapter<Message> {
	private ArrayList<Message> items;
	DateFormat formatter = null;
	private Context context = null;

    public MessageAdapter(Context context, int textViewResourceId, ArrayList<Message> items) {
            super(context, textViewResourceId, items);
            this.items = items;
            this.context = context;
            this.formatter =  new SimpleDateFormat("dd.MM.yyyy HH:mm");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = LayoutInflater.from(context);
                v = vi.inflate(R.layout.message_list_item, null);
            }
            Message o = items.get(position);
            if (o != null) {
                TextView tr = (TextView) v.findViewById(R.id.txt_message_recipient);
                TextView tt = (TextView) v.findViewById(R.id.txt_message_text);
                TextView td = (TextView) v.findViewById(R.id.txt_message_date);
                if (tr != null) {
                      tr.setText(o.phoneNumber); }
                if(tt != null){
                      tt.setText(o.message);
                }
                if(td != null && o.when !=null){
                    td.setText(formatter.format(o.when));
                }
            }
            
            v.setSelected(true);
            
            return v;
    }
}
