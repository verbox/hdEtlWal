package com.currencywarehouse.data.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.currencywarehouse.data.InMemoryDataInserter;
import com.currencywarehouse.data.RemoteXMLCurrencyLoader;

public class RemoteXMLCurrencyLoaderTest {
	private RemoteXMLCurrencyLoader loader;
	
	@Before
	public void before()
	{
		loader = new RemoteXMLCurrencyLoader();
	}
	
	@Test
	public void testLoadData() {
		/*InMemoryDataInserter di = new InMemoryDataInserter();
		loader.loadData(di);
		di.print();*/
	}

}
