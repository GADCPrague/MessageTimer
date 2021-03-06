package com.devcamp.messagetimer;

import java.text.SimpleDateFormat;

import android.text.format.DateFormat;

public final class MT
{
	public static final String MESSAGE_ID = "MESSAGE_ID";
	
	public static final SimpleDateFormat TIMEFORMAT = new SimpleDateFormat("HH:mm");
	public static final SimpleDateFormat DATEFORMAT = new SimpleDateFormat("dd.MM.yyyy");
	
	public static final int CONTACT_PICK_REQUEST = 0x974231;
	public static final String SEND_MESSAGE_ACTION = "SEND_MESSAGE_ACTION";
	public static final int SEND_MESSAGE_REQUEST_CODE = 0x78134;
}
