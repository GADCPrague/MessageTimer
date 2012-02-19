package com.devcamp.messagetimer.sender;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;

import com.devcamp.messagetimer.R;

import android.content.Context;
import android.util.Log;

public class GTalkSender extends Sender
{

	public GTalkSender(Context context)
	{
		super(context);
	}

	@Override
	public void sendMessage(String to, String message)
	{
		Log.v("SMSSender","sendMessage");
		
		String[] tos = getUserName().split("\\@");
		String name = tos[0];
		String domain = tos[1];
		
		ConnectionConfiguration connConfig = new ConnectionConfiguration("talk.google.com", 5222, domain);
		XMPPConnection connection = new XMPPConnection(connConfig);
		if(connConfig.isSASLAuthenticationEnabled())
			connConfig.setSASLAuthenticationEnabled(false);
		else
			connConfig.setSASLAuthenticationEnabled(true);
		Message msg = new Message();
		msg.setBody(message);
		try
		{

			connection.connect();			
			connection.login(name, getPasword());

			Presence presence = new Presence(Presence.Type.available);
            connection.sendPacket(presence);
            
			ChatManager chatmanager = connection.getChatManager();
			Chat chat = chatmanager.createChat(to, new MessageListener()
			{
				
				@Override
				public void processMessage(Chat arg0, Message arg1)
				{
					// TODO Auto-generated method stub
					
				}
			});
			chat.sendMessage(msg);
			connection.disconnect();
		}
		catch (XMPPException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String getUserName()
	{
		return getStringProperty(R.string.PROPERTY_GTALK_LOGIN);
	}
	
	private String getPasword()
	{
		return getStringProperty(R.string.PROPERTY_GTALK_PASSWORD);
	}

}
