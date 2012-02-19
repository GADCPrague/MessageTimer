package com.devcamp.messagetimer.sender;

import com.devcamp.messagetimer.R;

import android.content.Context;
import android.util.Log;

public class ICQSender extends Sender
{

	public ICQSender(Context context)
	{
		super(context);
	}

	@Override
	public void sendMessage(String to, String message)
	{
		Log.v("SMSSender","sendMessage");
		// Create a connection to the jabber.org server.
//		try
//		{
//			ConnectionConfiguration connConfig = new ConnectionConfiguration("login.icq.com",5238);
//			XMPPConnection connection = new XMPPConnection(connConfig);
//			if(connConfig.isSASLAuthenticationEnabled())
//				connConfig.setSASLAuthenticationEnabled(false);
////					else
////						connConfig.setSASLAuthenticationEnabled(true);
//			Message msg = new Message();
//			msg.setBody("test blablabla odpovez :P");
//			connection.connect();			
//			connection.login(getUserName(), getPasword());
//
//			Presence presence = new Presence(Presence.Type.available);
//            connection.sendPacket(presence);
//            
//			ChatManager chatmanager = connection.getChatManager();
//			Chat chat = chatmanager.createChat("318658859", new MessageParrot());
//			chat.sendMessage(msg);
//			connection.disconnect();
//		}
//		catch (XMPPException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	private String getUserName()
	{
		return getStringProperty(R.string.PROPERTY_ICQ_LOGIN);
	}
	
	private String getPasword()
	{
		return getStringProperty(R.string.PROPERTY_ICQ_PASSWORD);
	}
}
