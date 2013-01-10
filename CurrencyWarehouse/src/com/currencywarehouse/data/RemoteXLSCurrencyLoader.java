package com.currencywarehouse.data;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.commons.lang3.StringUtils;

import com.currencywarehouse.entities.Currency;

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

	private CurrencyXLSParser currencyXLSParser = new CurrencyXLSParser();

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

			List<Currency> currencies = currencyXLSParser.getCurrencyValues(workbook, year);
			if (!currencies.isEmpty()) {
				dataInserter.beginInsert();
				try {
					for (Currency currency : currencies) {
						dataInserter.insertCurrencyHistoryEntry(currency);
					}
				} finally {
					dataInserter.endInsert();
				}
			}
		} catch (IOException | BiffException e) {
			onError(e.toString());
			e.printStackTrace();
		}
	}

	public DataInserter getDataInserter() {
		return dataInserter;
	}

	public void setDataInserter(DataInserter dataInserter) {
		this.dataInserter = dataInserter;
	}

	public CurrencyXLSParser getCurrencyXLSParser() {
		return currencyXLSParser;
	}

	public void setCurrencyXLSParser(CurrencyXLSParser currencyXLSParser) {
		this.currencyXLSParser = currencyXLSParser;
	}
}
