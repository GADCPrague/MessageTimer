package com.devcamp.messagetimer.controls;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public class NoImeEditText extends EditText
{

	public NoImeEditText(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	public NoImeEditText(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public NoImeEditText(Context context)
	{
		super(context);
	}
	
	 @Override 
	    public boolean onCheckIsTextEditor() {
	        return false;
	    }       
	
}
