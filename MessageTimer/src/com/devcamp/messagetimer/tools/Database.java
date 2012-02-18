package com.devcamp.messagetimer.tools;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.devcamp.messagetimer.R;
import com.devcamp.messagetimer.model.Message;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper
{
	private static final int DATABASE_VERSION = 1;
	private static final int[] CREATE_DB_QUERIES = new int[]{R.string.SQL_CREATE_TABLE_MESSAGES};
	private static final String DB_NAME = "messagetimer.sqlite";
	private Context mContext = null;
	
	
	public Database(Context context)
	{
		super(context, DB_NAME, null, DATABASE_VERSION);
		mContext = context;
	}
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		createTables(db);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		///TODO
	}
	
	private void createTables(SQLiteDatabase db)
	{
		for(int i = 0;i<CREATE_DB_QUERIES.length;i++)
		{
			String qry = mContext.getString(CREATE_DB_QUERIES[i]);
			db.execSQL(qry);
		}
	}
	
	/**
	 * Delete data from all tables
	 */
	public void deleteAllTables()
	{
		SQLiteDatabase db = getWritableDatabase();
		for(int i = 0;i<Structure.Tables.ALL_TABLES.length;i++)
		{
			String qry = String.format("DELETE FROM %s", Structure.Tables.ALL_TABLES[i]);
			db.execSQL(qry);
		}
	}
	
	/**
	 * Returns all messages
	 * @return
	 */
	public List<Message> getMessages()
	{
		return getMessages(null);
	}
	
	/**
	 * Returns messages
	 * @param id for message, can be null. If null value is passed all records are returned
	 * @return 
	 */
	public List<Message> getMessages(Long id)
	{
		SQLiteDatabase db = getReadableDatabase();
		Cursor c = null;
		if(id == null)
		{
			c = db.query(Structure.Tables.MESSAGES, 
						Structure.Messages.ALL_COLUMNS, null,null,null,null,null);
		}
		else
		{
			c = db.query(Structure.Tables.MESSAGES, 
					Structure.Messages.ALL_COLUMNS,
					String.format("%s = ?",Structure.Messages.ID),
					new String[] {String.valueOf(id)},null,null,null);
		}
		List<Message> result = new ArrayList<Message>();
		while(c.moveToNext())
		{
			Message m = new Message();
			m.id = c.getLong(c.getColumnIndex(Structure.Messages.ID));
			m.phoneNumber = c.getString(c.getColumnIndex(Structure.Messages.PHONENUMBER));
			m.message = c.getString(c.getColumnIndex(Structure.Messages.MESSAGE));
			m.when = new Date(c.getLong(c.getColumnIndex(Structure.Messages.TIME)));
			m.isEnabled = convert(c.getShort(c.getColumnIndex(Structure.Messages.ENABLED)));
			result.add(m);
		}
		c.close();
		return result;
	}
	
	/**
	 * Add new message to DB<br> 
	 * {@link Messsage#ID} is changed to id 
	 * @param message
	 */
	public void addMessage(Message message)
	{
		SQLiteDatabase db = getWritableDatabase();
		
		ContentValues cv = new ContentValues();
		cv.put(Structure.Messages.PHONENUMBER, message.phoneNumber);
		cv.put(Structure.Messages.MESSAGE, message.message);
		cv.put(Structure.Messages.TIME, message.when.getTime());
		cv.put(Structure.Messages.ENABLED, convert(message.isEnabled));
		
		long id = db.insertOrThrow(Structure.Tables.MESSAGES, null, cv);
		message.id = id;
	}
	
	/**
	 * Delete message by id
	 * @param id
	 */
	public void deleteMessage(long id)
	{
		SQLiteDatabase db = getWritableDatabase();
		db.delete(Structure.Tables.MESSAGES,
					String.format("%s = ?", Structure.Messages.ID),
					new String[] {String.valueOf(id)});
	}
	
	
	
	
	private boolean convert(short s)
	{
		return s == 1;
	}
	
	private short convert(boolean b)
	{
		return (short) ((b) ? 1 : 0);
	}
	
	public static final class Structure
	{
		public final static class Tables
		{
			public static final String MESSAGES = "Messages";
			public static final String[] ALL_TABLES = new String[] {MESSAGES};
		}
		
		public final static class Messages
		{
			public static final String ID = "ID";
			public static final String PHONENUMBER = "PhoneNumber";
			public static final String MESSAGE = "Message";
			public static final String TIME = "Time";
			public static final String ENABLED = "Enabled";
			
			public static final String[] ALL_COLUMNS = new String[] {ID,PHONENUMBER,MESSAGE,TIME,ENABLED};
		}
	}
}
