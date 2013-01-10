package com.currencywarehouse.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import com.currencywarehouse.entities.Currency;

/**
 * Parser, wyciagajacy z pliku .xls dane na temat kursow walut. Na podstawie
 * przekazanego dokumentu .xls w postaci jxl.Workbook tworzy i zwraca liste
 * obiektow typu {@link com.currencywarehouse.entities.Currency}.
 * 
 * @author Monika Swoboda
 */
public class CurrencyXLSParser extends AbstractErrorReporter {

	/**
	 * Metoda na podstawie przekazanego dokumentu .xls tworzy liste zawartych w
	 * nim kursow walut.
	 * 
	 * @param workbook
	 * @param year
	 * @return
	 */
	public List<Currency> getCurrencyValues(Workbook workbook, String year) {		
		List<Currency> resultList = new ArrayList<Currency>();
		
		Sheet sheet = workbook.getSheet(0);

		Cell[] currencyCodesColumn = sheet.getColumn(1);
		for (Cell cell : currencyCodesColumn) {
			String currencyCode = cell.getContents();
			if (StringUtils.isNotEmpty(currencyCode) && StringUtils.startsWith(currencyCode, "1")) {
				Cell[] currencyValues = sheet.getRow(cell.getRow());
				for (Cell cell2 : currencyValues) {
					String currencyValue = cell2.getContents();
					if (StringUtils.isNotEmpty(currencyValue) && StringUtils.contains(currencyValue, ",") && cell2.getColumn() < 14) {
						BigDecimal decimalValue = new BigDecimal(currencyValue.replaceAll(",", "."));

						Currency currency = new Currency();
						currency.setCurrencyCode(currencyCode);
						currency.setValue(decimalValue);

						Calendar calendar = Calendar.getInstance();
						calendar.set(Calendar.YEAR, Integer.valueOf(year));
						calendar.set(Calendar.MONTH, cell2.getColumn() - 2);
						calendar.set(Calendar.DAY_OF_MONTH, 1);
						calendar.set(Calendar.HOUR_OF_DAY, 0);
						calendar.set(Calendar.MINUTE, 0);
						calendar.set(Calendar.SECOND, 0);
						currency.setDate(calendar.getTime());
						
						resultList.add(currency);
					}
				}
			}
		}
		return resultList;
	}	
}
