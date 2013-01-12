package com.currencywarehouse.gui;

import javax.swing.JTextArea;

import com.currencywarehouse.data.ErrorListener;

public class TextAreaErrorListener implements ErrorListener {
	private JTextArea textAreaOutput;
	
	public TextAreaErrorListener(JTextArea textAreaOutput) {
		super();
		this.textAreaOutput = textAreaOutput;
	}

	public void onError(String errorMessage) {
		addMessage(errorMessage);
	}
	
	public synchronized void addMessage(String errorMessage) {
		textAreaOutput.append(errorMessage);
	}

}
