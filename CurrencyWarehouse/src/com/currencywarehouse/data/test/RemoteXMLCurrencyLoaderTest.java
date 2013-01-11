package com.currencywarehouse.data.test;

import org.junit.Before;
import org.junit.Test;

import com.currencywarehouse.data.RemoteXMLCurrencyLoader;

public class RemoteXMLCurrencyLoaderTest {
	@Before
	public void before()
	{
		new RemoteXMLCurrencyLoader();
	}
	
	@Test
	public void testLoadData() {
		/*InMemoryDataInserter di = new InMemoryDataInserter();
		loader.loadData(di);
		di.print();*/
	}

}
