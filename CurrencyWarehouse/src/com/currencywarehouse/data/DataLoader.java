package com.currencywarehouse.data;

public interface DataLoader {
	public void loadData(DataInserter dataInserter);
	
	/**
	 * Sets work listener - connect to MainFrame
	 */
	public void setWorkListener(WorkListener workListener);
}
