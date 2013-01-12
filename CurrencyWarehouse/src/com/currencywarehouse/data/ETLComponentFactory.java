package com.currencywarehouse.data;

public class ETLComponentFactory {
	public static DataLoader getLoader(int id) {
		switch(id) {
		case 0: {
			return new RemoteXLSCurrencyLoader();
		}
		case 1: {
			return new RemoteXMLCurrencyLoader();
		}
		default: {
			return new RemoteXMLCurrencyLoader();
		}
		}
	}
	
	public static DataInserter getInserter(int id) {
		switch(id) {
		case 0: {
			return new InMemoryDataInserter();
		}
		case 1: {
			return new DatabaseDataInserter();
		}
		default: {
			return new InMemoryDataInserter();
		}
		}
	}
}
