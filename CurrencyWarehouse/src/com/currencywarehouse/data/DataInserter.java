package com.currencywarehouse.data;

import com.currencywarehouse.entities.Currency;

public interface DataInserter {
	/**
	 * Begins new transaction
	 */
	public void beginInsert();
	public void insertCurrencyHistoryEntry(Currency currency);
	
	/**
	 * Ends current transaction
	 */
	public void endInsert();
}
