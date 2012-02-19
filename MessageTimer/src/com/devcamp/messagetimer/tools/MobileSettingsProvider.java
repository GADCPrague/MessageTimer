package com.devcamp.messagetimer.tools;

import java.util.HashMap;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.provider.Settings.NameValueTable;
import android.telephony.TelephonyManager;

public class MobileSettingsProvider
{

	public static final int TIME_FORMAT_12H = 12;
	public static final int TIME_FORMAT_24H = 24;
	public static final int NETWORK_WIFI = ConnectivityManager.TYPE_WIFI;
	

	// returns timeformat
	public static int getTimeFormat(Context context)
	{
		String timeVal = getValue(context, Settings.System.TIME_12_24);
		if (timeVal == null)
			timeVal = String.valueOf(TIME_FORMAT_24H);
		int value = Integer.parseInt(timeVal);
		return value;
	}
	

	// returns single preference
	private static String getValue(Context c, String prefName)
	{
		ContentResolver cr = c.getContentResolver();
		String[] from = { NameValueTable.VALUE };
		String where = String.format("%s = ?", NameValueTable.NAME);
		String[] whereArgs = new String[] { prefName };
		Cursor cursor = cr.query(Settings.System.CONTENT_URI, from, where, whereArgs, null);

		String result = null;
		if (cursor.moveToFirst())
			result = cursor.getString(cursor.getColumnIndex(NameValueTable.VALUE));
		cursor.close();

		return result;
	}	

	// returns all preferences
	public static HashMap<String, String> getPreferences(Context context)
	{
		ContentResolver cr = context.getContentResolver();
		String[] from = { NameValueTable.NAME, NameValueTable.VALUE };
		Cursor cursor = cr.query(Settings.System.CONTENT_URI, from, null, null, null);
		HashMap<String, String> result = new HashMap<String, String>();
		while (cursor.moveToNext())
		{
			String name = cursor.getString(0);
			String value = cursor.getString(1);
			result.put(name, value);
		}
		cursor.close();
		return result;
	}
	
	public static int getConnectionType(Context context)
	{
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if(info != null)
		{
			int netType = info.getType();
			return netType;
		}
		return ConnectivityManager.TYPE_MOBILE;
	}
	
	public static boolean getDataConnected(Context context)
	{
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);		
		return (tm.getDataState() == TelephonyManager.DATA_CONNECTED 
					|| getConnectionType(context) == ConnectivityManager.TYPE_WIFI);
	}
}
