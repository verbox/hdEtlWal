package com.currencywarehouse.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.commons.lang3.StringUtils;

/**
 * Loader łączy się z adresem
 * http://www.nbp.pl/kursy/archiwum/wagi_archiwum_XXXX.xls gdzie XXXX to rok od
 * 1996 do 2002, pobiera plik xls pod podanym adresem, parsuje go do listy
 * obiektów Currency i ładuje tę listę za pomocą przekazanego w metodzie
 * loadData insertera.
 * 
 * @author Monika Swoboda
 */
public class RemoteXLSCurrencyLoader extends AbstractErrorReporter implements DataLoader {

	private DataInserter dataInserter;

	public static String url = "http://www.nbp.pl/kursy/archiwum/wagi_archiwum_";
	
	public static String[] years = { "1996", "1997", "1998", "1999", "2000", "2001", "2002" };
	
	@Override
	public void loadData(DataInserter dataInserter) {
		this.dataInserter = dataInserter;
		
		for (String year : years) {
			fetchAndInsertFromRemoteXls(year);
		}
	}

	
	/**
	 * Metoda łączy się z adresem
	 * http://www.nbp.pl/kursy/archiwum/wagi_archiwum_year.xls gdzie year to rok
	 * przekazany jako parametr. Parsuje otrzymanego pod ww. adresem xls'a i
	 * laduje z niego waluty za pomoca insertera.
	 * 
	 * @param year
	 */
	public void fetchAndInsertFromRemoteXls(String year) {
		if (StringUtils.isBlank(year)) {
			System.out.println("Empty parameter year in method fetchAndInsertFromRemoteXls");
			return;
		}

		URL currencyXlsUrl;
		try {
			currencyXlsUrl = new URL(url + year + ".xls");
			URLConnection yc = currencyXlsUrl.openConnection();
			
			Workbook workbook = Workbook.getWorkbook(yc.getInputStream());
			Sheet sheet = workbook.getSheet(0);
			
			Cell a7 = sheet.getCell(0,6); 
			Cell b7 = sheet.getCell(1,6); 
			Cell c7 = sheet.getCell(2,6); 
			String stringa1 = a7.getContents(); 
			String stringb7 = b7.getContents(); 
			String stringc7 = c7.getContents();
			
			System.out.println(stringa1);
			System.out.println(stringb7);
			System.out.println(stringc7);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		}
	}

	public DataInserter getDataInserter() {
		return dataInserter;
	}

	public void setDataInserter(DataInserter dataInserter) {
		this.dataInserter = dataInserter;
	}
}
