package com.devcamp.messagetimer.tools;

import java.util.List;

import com.devcamp.messagetimer.model.Message;
import com.devcamp.messagetimer.test.DataComparator;
import com.devcamp.messagetimer.test.DataGenerator;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;
import junit.framework.TestCase;

public class DatabaseTest extends AndroidTestCase
{
	Database db;
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		db = new Database(mContext);
	}
	
	@Override
	protected void tearDown() throws Exception
	{
		super.tearDown();
		db.deleteAllTables();
	}
	
	
	public void testAddGetDeleteGroupMessages()
	{
		List<Message> data = DataGenerator.getRandomMessages(50);
		for(int i = 0;i<data.size();i++)
		{
			Message m = data.get(i);
			try
			{
				db.addMessage(m);
			}
			catch(Exception e)
			{
				String msg = e.getMessage();
			}
			assertTrue(m.id > 0);
		}
		
		List<Message> fromDb = db.getMessages();
		
		DataComparator.assertEquals(data,fromDb,true);
		
		db.deleteAllTables();
		
		fromDb = db.getMessages();
		DataComparator.assertEquals(0,fromDb.size());
	}

	public void testGetDeleteMessagesLong()
	{
		Message m1 = DataGenerator.getRandomMessage();
		Message m2 = DataGenerator.getRandomMessage();
		
		db.addMessage(m1);
		db.addMessage(m2);
		
		assertEquals(2, db.getMessages().size());
		
		
		//test first item
		List<Message> fromDb = db.getMessages(m1.id);
		assertEquals(1, fromDb.size());
		Message db1 = fromDb.get(0);
		DataComparator.assertEquals(m1, db1, true);
		
		
		//test sescond item
		fromDb = db.getMessages(m2.id);
		assertEquals(1, fromDb.size());
		Message db2 = fromDb.get(0);
		DataComparator.assertEquals(m2, db2, true);
		
		//delete first
		db.deleteMessage(m1.id);
		assertEquals(1, db.getMessages().size());
		
		
		//test second again
		fromDb = db.getMessages();
		assertEquals(1, fromDb.size());
		Message db3 = fromDb.get(0);
		DataComparator.assertEquals(m2, db3, true);
	}
}
