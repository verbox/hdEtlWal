package com.currencywarehouse.data;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.currencywarehouse.entities.Currency;

public class RemoteXMLCurrencyLoader extends AbstractErrorReporter implements DataLoader {

	private DataInserter dataInserter;
	private WorkListener workListener = new EmptyWorkListener();
	
	public void setWorkListener(WorkListener workListener) {
		this.workListener = workListener;
	}

	@Override
	public void loadData(DataInserter dataInserter) {
		this.dataInserter = dataInserter;
		List<String> dirs = downloadDirectories();
		workListener.countAllTasks(dirs.size());
		for(String dir: dirs)
		{
			fetchAndInsertFromURL(dir);
			workListener.doneTask("Done ETL operations: "+dir);
		}
	}
	
	private List<String> downloadDirectories()
	{
		List<String> result = new ArrayList<String>();
		try {
			URL dirListLocation = new URL("http://www.nbp.pl/kursy/xml/dir.txt");
			BufferedInputStream inStream = new BufferedInputStream(dirListLocation.openStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
			try
			{
				String line = reader.readLine();
				while(line != null)
				{
					if(line.startsWith("a"))
					{
						String fileLocation = "http://www.nbp.pl/kursy/xml/" + line + ".xml";
						result.add(fileLocation);
					}
					line = reader.readLine();
				}
			}  finally
			{
				reader.close();
			}
		} catch (IOException e) {
			onError(e.toString());
			e.printStackTrace();
		} 
		return result;
	}

	private void fetchAndInsertFromURL(String url)
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
			Document document = db.parse(url);
			CurrencyXMLParser parser = new CurrencyXMLParser();
			List<Currency> currencyValues = parser.getCurrencyValues(document);
			dataInserter.beginInsert();
			try
			{
				for(Currency c: currencyValues)
				{
					dataInserter.insertCurrencyHistoryEntry(c);
				}
			}
			finally
			{
				dataInserter.endInsert();
			}
						
		} catch (ParserConfigurationException | SAXException | IOException e) {
			onError(e.toString());
			e.printStackTrace();
		}

	}
}
