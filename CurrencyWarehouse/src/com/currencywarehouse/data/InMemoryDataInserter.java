package com.currencywarehouse.data;

import java.util.ArrayList;
import java.util.List;

import com.currencywarehouse.entities.Currency;

public class InMemoryDataInserter implements DataInserter {
	List<Currency> insertedCurrencies = new ArrayList<Currency>();
	@Override
	public void beginInsert() {	
	}

	@Override
	public void insertCurrencyHistoryEntry(Currency currency) {
		insertedCurrencies.add(currency);		
	}

	@Override
	public void endInsert() {
	}

	public void print() {
		for(Currency c: insertedCurrencies)
		{
			System.out.println(c.toString());
		}
	}
	
	public List<Currency> getCurrencies() {
		return insertedCurrencies;
	}

}
