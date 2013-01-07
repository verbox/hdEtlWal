package com.currencywarehouse.data;

import java.sql.Connection;

public interface DataLoader {
	public void loadData(Connection databaseConnection);
}
