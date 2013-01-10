package com.currencywarehouse.data.test;

import org.junit.Before;
import org.junit.Test;

import com.currencywarehouse.data.InMemoryDataInserter;
import com.currencywarehouse.data.RemoteXLSCurrencyLoader;

/**
 * @author Monika Swoboda
 */
public class RemoteXLSCurrencyLoaderTest {

	private RemoteXLSCurrencyLoader loader;
	
	@Before
	public void before()
	{
		loader = new RemoteXLSCurrencyLoader();
	}
	
	@Test
	public void testLoadData() {
		InMemoryDataInserter di = new InMemoryDataInserter();
		loader.loadData(di);
		di.print();
	}

}
