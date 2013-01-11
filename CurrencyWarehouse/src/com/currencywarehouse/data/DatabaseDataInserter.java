package com.currencywarehouse.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.currencywarehouse.entities.Currency;

public class DatabaseDataInserter implements DataInserter {
	private Connection sqlConnection;
	private PreparedStatement stmt;
	
	@Override
	public void beginInsert() {
		stmt = null;
		sqlConnection = SQLConnectionFactory.createConnection();
		if(sqlConnection != null)
		{
			try {
				stmt = sqlConnection.prepareStatement("INSERT INTO CURRENCY_HISTORY(CURRENCY_CODE, CURRENCY_DATE, CURRENCY_VALUE) VALUES(:CURRENCY_CODE, :CURRENCY_DATE, :CURRENCY_VALUE)");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void insertCurrencyHistoryEntry(Currency currency) {
		if(sqlConnection == null || stmt == null)
		{
			return;
		}
		try {
			stmt.setString(1, currency.getCurrencyCode());
			stmt.setDate(2, new java.sql.Date(currency.getDate().getTime()));
			stmt.setBigDecimal(3, currency.getValue());
			stmt.addBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void endInsert() {
		if(stmt != null)
		{
			try {
				stmt.executeBatch();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			stmt = null;
		}
		if(sqlConnection != null)
		{
			try {
				sqlConnection.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		sqlConnection = null;
		
	}

}
