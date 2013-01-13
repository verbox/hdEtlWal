package com.currencywarehouse.gui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import com.currencywarehouse.data.DataInserter;
import com.currencywarehouse.data.DataLoader;
import com.currencywarehouse.data.WorkListener;

public class SWorkerETL extends SwingWorker<Void,String> implements WorkListener{
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
	protected Void doInBackground() throws Exception {
		btnETL.setEnabled(false);
		dataLoader.loadData(dataInserter);
		mainFrame.addMessageToOutput("ETL operations completed!");
		btnETL.setEnabled(true);
		return null;
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
