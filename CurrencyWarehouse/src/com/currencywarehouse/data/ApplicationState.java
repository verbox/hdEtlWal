package com.currencywarehouse.data;

import java.sql.Connection;

public class ApplicationState {
	//sigleton pattern
	private static ApplicationState appState;
	private static Object synchObj;
	
	private ApplicationState() {
		super();
	}
	
	public static ApplicationState getState() {
		if (appState==null)
			synchronized (synchObj) {
				appState = new ApplicationState();
			}
		return appState;
	}
	//end singleton pattern
	private Connection connection;

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
}
