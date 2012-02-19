package com.devcamp.messagetimer.tools;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.devcamp.messagetimer.R;
import com.devcamp.messagetimer.model.Message;
import com.devcamp.messagetimer.model.TemplateText;
import com.devcamp.messagetimer.tools.Database.Structure.Messages;

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
	private static final int[] CREATE_DB_QUERIES = new int[]{R.string.SQL_CREATE_TABLE_MESSAGES, R.string.SQL_CREATE_TABLE_TEMPLATES};
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
			m.service = c.getString(c.getColumnIndex(Structure.Messages.SERVICE));
			m.id = c.getLong(c.getColumnIndex(Structure.Messages.ID));
			m.contact = c.getString(c.getColumnIndex(Structure.Messages.CONCACTNAME));
			m.phoneNumber = c.getString(c.getColumnIndex(Structure.Messages.PHONENUMBER));
			m.message = c.getString(c.getColumnIndex(Structure.Messages.MESSAGE));
			m.when = new Date(c.getLong(c.getColumnIndex(Structure.Messages.TIME)));
			m.isEnabled = convert(c.getShort(c.getColumnIndex(Structure.Messages.ENABLED)));
			m.isTimeEnabled = convert(c.getShort(c.getColumnIndex(Structure.Messages.ISTIMEENABLED)));
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
		ContentValues cv = getContentValues(message);
		long id = db.insertOrThrow(Structure.Tables.MESSAGES, null, cv);
		message.id = id;
	}
	
	/**
	 * Updates current record<br>
	 * @param message if {@link Message#id} is null value is added
	 */
	public void updateMessage(Message message)
	{
		if(message.id == 0)
		{
			 addMessage(message);
			 return;
		}
		
		ContentValues cv = getContentValues(message);
		
		SQLiteDatabase db = getWritableDatabase();
		db.update(Structure.Tables.MESSAGES, cv, String.format("%s = ?", Structure.Messages.ID), new String[] {String.valueOf(message.id)});
	}
	
	private ContentValues getContentValues(Message message)
	{
		ContentValues cv = new ContentValues();
		cv.put(Structure.Messages.SERVICE, message.service);
		cv.put(Structure.Messages.PHONENUMBER, message.phoneNumber);
		cv.put(Structure.Messages.MESSAGE, message.message);
		cv.put(Structure.Messages.TIME, message.when.getTime());
		cv.put(Structure.Messages.ENABLED, convert(message.isEnabled));
		cv.put(Structure.Messages.CONCACTNAME, message.contact);
		cv.put(Structure.Messages.ISTIMEENABLED, convert(message.isTimeEnabled));
		return cv;
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
	
	
	public void addTemplate(TemplateText tt)
	{
		SQLiteDatabase db = getWritableDatabase();
		
		ContentValues cv = new ContentValues();
		cv.put(Structure.TemplateText.NAME, tt.name);
		cv.put(Structure.TemplateText.VALUE, tt.value);
		
		long id = db.insertOrThrow(Structure.Tables.TEMPLATES, null, cv);
		tt.id = id;
	}
	
	public void updateTemplate(TemplateText tt)
	{
		if(tt.id == 0)
		{
			addTemplate(tt);
			return;
		}
		
		SQLiteDatabase db = getWritableDatabase();
		
		ContentValues cv = new ContentValues();
		cv.put(Structure.TemplateText.NAME, tt.name);
		cv.put(Structure.TemplateText.VALUE, tt.value);
		
		db.update(Structure.Tables.TEMPLATES, cv, String.format("%s = ?", Structure.TemplateText.ID), new String[] {String.valueOf(tt.id)});
		
	}
	
	
	public List<TemplateText> getTemplates()
	{
		return getTemplates(null);
	}
	public List<TemplateText> getTemplates(Long id)
	{
		SQLiteDatabase db = getReadableDatabase();
		Cursor c = null;
		if(id == null)
		{
			c = db.query(Structure.Tables.TEMPLATES, 
						Structure.TemplateText.ALL_COLUMNS, null,null,null,null,null);
		}
		else
		{
			c = db.query(Structure.Tables.TEMPLATES, 
					Structure.TemplateText.ALL_COLUMNS,
					String.format("%s = ?",Structure.TemplateText.ID),
					new String[] {String.valueOf(id)},null,null,null);
		}
		List<TemplateText> result = new ArrayList<TemplateText>();
		while(c.moveToNext())
		{
			TemplateText tt = new TemplateText();
			tt.id = c.getLong(c.getColumnIndex(Structure.Messages.ID));
			tt.name = c.getString(c.getColumnIndex(Structure.TemplateText.NAME));
			tt.value = c.getString(c.getColumnIndex(Structure.TemplateText.VALUE));
			result.add(tt);
		}
		c.close();
		return result;
	}
	
	public void deleteTemplate(long id)
	{
		SQLiteDatabase db = getWritableDatabase();
		db.delete(Structure.Tables.TEMPLATES,
					String.format("%s = ?", Structure.TemplateText.ID),
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
			public static final String TEMPLATES = "Templates";
			public static final String[] ALL_TABLES = new String[] {MESSAGES, TEMPLATES};
		}
		
		public final static class Messages
		{
			public static final String ID = "ID";
			public static final String PHONENUMBER = "PhoneNumber";
			public static final String MESSAGE = "Message";
			public static final String TIME = "Time";
			public static final String ENABLED = "Enabled";
			public static final String CONCACTNAME = "ConcactName";
			public static final String ISTIMEENABLED = "IsTimeEnabled";
			public static final String SERVICE = "Service";
			
			
			public static final String[] ALL_COLUMNS = new String[] {ID,PHONENUMBER,MESSAGE,TIME,ENABLED,CONCACTNAME,ISTIMEENABLED,SERVICE};
		}
		
		public final static class TemplateText
		{
			public static final String ID = "ID";
			public static final String NAME = "Name";
			public static final String VALUE = "Value";
			public static final String[] ALL_COLUMNS = new String[] {ID, NAME, VALUE};
		}
	}
}
