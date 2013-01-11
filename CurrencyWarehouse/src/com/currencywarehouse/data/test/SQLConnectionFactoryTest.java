package com.currencywarehouse.data.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.currencywarehouse.data.SQLConnectionFactory;

public class SQLConnectionFactoryTest {

	@Test
	public void testCreateConnection() {
		assertNotNull(SQLConnectionFactory.createConnection());
	}

}
