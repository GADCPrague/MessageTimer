package handcode;

import java.util.List;

import android.test.AndroidTestCase;

import com.devcamp.messagetimer.model.TemplateText;
import com.devcamp.messagetimer.test.DataGenerator;
import com.devcamp.messagetimer.tools.Database;

import junit.framework.TestCase;

public class HandTest extends AndroidTestCase
{
	public void testAdd3Templates()
	{
		List<TemplateText> data = DataGenerator.getRandomTemplates(3);
		Database db = new Database(mContext);
		for(TemplateText tt : data)
			db.addTemplate(tt);
	}
}
