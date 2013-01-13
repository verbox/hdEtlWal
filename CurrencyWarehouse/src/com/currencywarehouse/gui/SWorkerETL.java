package com.currencywarehouse.gui;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import com.currencywarehouse.data.DataInserter;
import com.currencywarehouse.data.DataLoader;
import com.currencywarehouse.data.InMemoryDataInserter;
import com.currencywarehouse.data.WorkListener;
import com.currencywarehouse.entities.Currency;

public class SWorkerETL extends SwingWorker<List<Currency>,String> implements WorkListener{
	int countOfAll;
	int currentPosition;
	MainFrame mainFrame;
	DataInserter dataInserter;
	DataLoader dataLoader;
	
	//controllers
	private JButton btnETL;
	private JLabel jlbProgress;
	private JProgressBar jProgressBar;
	
	public SWorkerETL(MainFrame mainFrame, JButton btnETL, JLabel jlbProgress, JProgressBar jProgressBar) {
		super();
		this.mainFrame = mainFrame;
		this.btnETL = btnETL;
		this.jlbProgress = jlbProgress;
		this.jProgressBar = jProgressBar;
	}

	public void setETLElements(DataLoader datal, DataInserter datai) {
		dataLoader = datal;
		dataInserter = datai;
		dataLoader.setWorkListener(this);
	}
	
	@Override
	protected List<Currency> doInBackground() throws Exception {
		btnETL.setEnabled(false);
		dataLoader.loadData(dataInserter);
		mainFrame.addMessageToOutput("ETL operations completed!");
		btnETL.setEnabled(true);
		//nie paczaæ
		return (dataInserter instanceof InMemoryDataInserter ? ((InMemoryDataInserter) dataInserter).getCurrencies() : null);
	}
	
	@Override
	protected void done() {
		try {
			if (get()!=null) {
				mainFrame.prepareListOfCurrenciesFrame(get());
			}
		}
		catch (ExecutionException | InterruptedException ex){
			mainFrame.addMessageToOutput(ex.getMessage()+"\nError with data inserter.");
		}
	}
	
	@Override
	public void countAllTasks(int count) {
		countOfAll = count;
		currentPosition = 1;
		jProgressBar.setValue(0);
		jProgressBar.setStringPainted(true);
	}

	@Override
	public void doneTask(String msg) {
		mainFrame.addMessageToOutput(msg);
		jProgressBar.setValue(currentPosition*100/countOfAll);
		currentPosition++;
	}
	
}
