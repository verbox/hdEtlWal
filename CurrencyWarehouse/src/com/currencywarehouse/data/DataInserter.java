package com.currencywarehouse.data;

import com.currencywarehouse.entities.Currency;

public interface DataInserter {
	public void insertCurrencyHistoryEntry(Currency currency);
}
