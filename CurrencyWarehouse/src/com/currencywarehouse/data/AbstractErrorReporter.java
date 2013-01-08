package com.currencywarehouse.data;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractErrorReporter {
	private List<ErrorListener> listeners = new ArrayList<ErrorListener>();
	public void addErrorListener(ErrorListener listener)
	{
		listeners.add(listener);
	}
	
	public void removeListener(ErrorListener listener)
	{
		listeners.remove(listener);
	}
	
	public void onError(String error)
	{
		for(ErrorListener listener: listeners)
		{
			listener.onError(error);
		}
	}
}
