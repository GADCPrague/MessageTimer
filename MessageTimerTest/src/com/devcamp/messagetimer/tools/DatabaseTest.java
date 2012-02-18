package com.devcamp.messagetimer.tools;

import java.util.List;

import com.devcamp.messagetimer.model.Message;
import com.devcamp.messagetimer.model.TemplateText;
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
		db.deleteAllTables();
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
		
		DataComparator.assertEqualsMessages(data,fromDb,true);
		
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
	
	public void testUpdateMessage()
	{
		Message m1 = DataGenerator.getRandomMessage();
		db.addMessage(m1);
		Message m2 = DataGenerator.getRandomMessage();
		
		m2.id = m1.id;
		
		db.updateMessage(m2);
		Message fromDb = db.getMessages(m2.id).get(0);
		DataComparator.assertEquals(m2, fromDb, true);
	}
	
	public void testAddGetDeleteTextTemplates()
	{
		List<TemplateText> data = DataGenerator.getRandomTemplates(50);
		for(int i = 0;i<data.size();i++)
		{
			TemplateText m = data.get(i);
			try
			{
				db.addTemplate(m);
			}
			catch(Exception e)
			{
				String msg = e.getMessage();
			}
			assertTrue(m.id > 0);
		}
		
		List<TemplateText> fromDb = db.getTemplates(null);
		
		DataComparator.assertEqualsTemplateTexts(data,fromDb,true);
		
		db.deleteAllTables();
		
		fromDb = db.getTemplates(null);
		DataComparator.assertEquals(0,fromDb.size());
	}
	
	public void testGetDeleteTemplatesLong()
	{
		TemplateText m1 = DataGenerator.getRandomTemplateText();
		TemplateText m2 = DataGenerator.getRandomTemplateText();
		
		db.addTemplate(m1);
		db.addTemplate(m2);
		
		assertEquals(2, db.getTemplates().size());
		
		
		//test first item
		List<TemplateText> fromDb = db.getTemplates(m1.id);
		assertEquals(1, fromDb.size());
		TemplateText db1 = fromDb.get(0);
		DataComparator.assertEquals(m1, db1, true);
		
		
		//test sescond item
		fromDb = db.getTemplates(m2.id);
		assertEquals(1, fromDb.size());
		TemplateText db2 = fromDb.get(0);
		DataComparator.assertEquals(m2, db2, true);
		
		//delete first
		db.deleteTemplate(m1.id);
		assertEquals(1, db.getTemplates().size());
		
		
		//test second again
		fromDb = db.getTemplates();
		assertEquals(1, fromDb.size());
		TemplateText db3 = fromDb.get(0);
		DataComparator.assertEquals(m2, db3, true);
	}
	
	public void testUpdateTemplates()
	{
		TemplateText m1 = DataGenerator.getRandomTemplateText();
		db.addTemplate(m1);
		TemplateText m2 = DataGenerator.getRandomTemplateText();
		
		m2.id = m1.id;
		
		db.updateTemplate(m2);
		TemplateText fromDb = db.getTemplates(m2.id).get(0);
		DataComparator.assertEquals(m2, fromDb, true);
	}
}
