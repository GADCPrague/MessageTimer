package com.devcamp.messagetimer.tools;

import java.util.HashMap;
import java.util.Map;

import com.devcamp.messagetimer.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Resources;
import android.preference.PreferenceManager;


/**
 * Implementation of property provider, like singleton
 * 
 * @author Joe Scurab
 * 
 */
public class PropertyProvider implements SharedPreferences, OnSharedPreferenceChangeListener
{

	private SharedPreferences mSettings;
	private SharedPreferences.Editor mEditor;

	private HashMap<String, OnSharedPreferenceChangeListener> mListeners = new HashMap<String, OnSharedPreferenceChangeListener>();
	private static PropertyProvider mSelf;
	private Context mContext;
	private Resources mResources;

	public static PropertyProvider getProvider(Context context)
	{
		if (mSelf == null)
			mSelf = new PropertyProvider(context);
		return mSelf;
	}

	private PropertyProvider(Context context)
	{
		this.mContext = context;
		this.mResources = this.mContext.getResources();
		this.mSettings = PreferenceManager.getDefaultSharedPreferences(context);
		this.mSettings.registerOnSharedPreferenceChangeListener(this);
		this.mEditor = mSettings.edit();
	}

	public boolean getBoolean(int propertyId, boolean defValue)
	{
		String propertyName = this.transformToKey(propertyId);
		return this.mSettings.getBoolean(propertyName, defValue);
	}

	@Override
	public boolean getBoolean(String propertyName, boolean defValue)
	{
		return this.mSettings.getBoolean(propertyName, defValue);
	}

	public int getInt(int propertyId, int defValue)
	{
		String propertyName = this.transformToKey(propertyId);
		return this.mSettings.getInt(propertyName, defValue);
	}

	@Override
	public int getInt(String propertyName, int defValue)
	{
		return this.mSettings.getInt(propertyName, defValue);
	}

	public long getLong(int propertyId, long defValue)
	{
		String propertyName = this.transformToKey(propertyId);
		return this.mSettings.getLong(propertyName, defValue);
	}

	@Override
	public long getLong(String key, long defValue)
	{
		return this.mSettings.getLong(key, defValue);
	}

	public float getFloat(int propertyId, float defValue)
	{
		String propertyName = this.transformToKey(propertyId);
		return this.mSettings.getFloat(propertyName, defValue);
	}

	@Override
	public float getFloat(String propertyName, float defValue)
	{
		return this.mSettings.getFloat(propertyName, defValue);
	}

//	public String getString(int propertyId)
//	{
//		String propertyName = this.transformToKey(propertyId);
//		return this.mSettings.getString(propertyName, null);
//	}

//	public String getString(String propertyName)
//	{
//		return this.mSettings.getString(propertyName, null);
//	}

	public String getString(int propertyId, String defaultValue)
	{
		String propertyName = this.transformToKey(propertyId);
		return this.mSettings.getString(propertyName, defaultValue);
	}

	@Override
	public String getString(String propertyName, String defaultValue)
	{
		return this.mSettings.getString(propertyName, defaultValue);
	}

	public void setProperty(int propertyId, int value)
	{
		String propertyName = this.transformToKey(propertyId);
		this.setProperty(propertyName, value);
	}

	public void setProperty(String propertyName, int value)
	{
		this.mEditor.putInt(propertyName, value);
		this.mEditor.commit();
	}

	public void setProperty(int propertyId, float value)
	{
		String propertyName = this.transformToKey(propertyId);
		this.setProperty(propertyName, value);
	}

	public void setProperty(String propertyName, float value)
	{
		this.mEditor.putFloat(propertyName, value);
		this.mEditor.commit();
	}
	
	public void setProperty(int propertyId, long value)
	{
		String propertyName = this.transformToKey(propertyId);
		this.setProperty(propertyName, value);
	}

	public void setProperty(String propertyName, long value)
	{
		this.mEditor.putLong(propertyName, value);
		this.mEditor.commit();
	}

	public void setProperty(int propertyId, String value)
	{
		String propertyName = this.transformToKey(propertyId);
		this.setProperty(propertyName, value);
	}

	public void setProperty(String propertyName, String value)
	{
		this.mEditor.putString(propertyName, value);
		this.mEditor.commit();
	}

	public void setProperty(int propertyId, Boolean value)
	{
		String propertyName = this.transformToKey(propertyId);
		this.setProperty(propertyName, value);
	}

	public void setProperty(String propertyName, Boolean value)
	{
		this.mEditor.putBoolean(propertyName, value);
		this.mEditor.commit();
	}

	@Override
	public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener)
	{
		this.mListeners.put(listener.toString(), listener);
	}

	@Override
	public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener)
	{
		this.mListeners.remove(listener);
	}

	public void unregisterOnSharedPreferenceChangeListeners()
	{
		this.mListeners.clear();
	}

	private void notifyListeners(SharedPreferences sharedPreferences, String key)
	{
		for (String keyListener : mListeners.keySet())
		{
			OnSharedPreferenceChangeListener prefList = mListeners.get(keyListener);
			prefList.onSharedPreferenceChanged(sharedPreferences, key);
		}
	}

	@Override
	public boolean contains(String key)
	{
		return this.mSettings.contains(key);
	}

	/**
	 * {@see SharedPreferences#edit()}
	 */
	@Override
	public Editor edit()
	{
		return this.mSettings.edit();
	}

	/**
	 * {@see SharedPreferences#getAll()}
	 */
	@Override
	public Map<String, ?> getAll()
	{
		
		return this.mSettings.getAll();		
	}

	/**
	 * Transform id to string reprezentation, but name and value must be SAME!
	 * 
	 * @param id
	 * @return
	 */
	public String transformToKey(int id)
	{
		return this.mResources.getString(id);
	}

	/**
	 * Value and name must be exact SAME, or this method will not work
	 * 
	 * @param key
	 * @return
	 */
	public int transformToId(String key)
	{
		return this.transformToId("string", key);
	}

	public int transformToId(String namespace, String key)
	{		
		int value = this.mResources.getIdentifier(key, namespace, R.class.getPackage().getName());
		return value;
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
	{
		this.notifyListeners(sharedPreferences, key);
	}
}
