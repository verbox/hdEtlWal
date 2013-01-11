package com.currencywarehouse.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JDialog;

import com.currencywarehouse.gui.LoginDialog;

public class SQLConnectionFactory {
	private static Connection instance;
	public static synchronized Connection createConnection()
	{
		if(instance == null)
		{
			try {
				LoginDialog loginDialog = new LoginDialog();
				loginDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				loginDialog.setModal(true);
				loginDialog.setVisible(true);
				Class.forName("oracle.jdbc.driver.OracleDriver");
				String url = "jdbc:oracle:thin:@oracle2.icis.pcz.pl:1521:icis";
				
				instance = DriverManager.getConnection(url, loginDialog.getLogin(), loginDialog.getPassword());
				instance.setAutoCommit(false);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
		return instance;
	}
}
