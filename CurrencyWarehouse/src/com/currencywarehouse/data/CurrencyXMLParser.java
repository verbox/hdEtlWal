package com.currencywarehouse.data;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.currencywarehouse.entities.Currency;

public class CurrencyXMLParser extends AbstractErrorReporter{
	private Date dateOfCourse;
	
	private void extractDateOfDocument(Document document)
	{
		NodeList dataNode = document.getElementsByTagName("data_publikacji");
		if(dataNode.getLength() == 1)
		{
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dateOfCourse = df.parse(dataNode.item(0).getFirstChild().getNodeValue());
			} catch (DOMException | ParseException e) {
				onError(e.toString());
				e.printStackTrace();
			}
		}
	}
	
	private Currency newCurrency()
	{
		Currency result = new Currency();
		result.setDate(dateOfCourse);
		return result;
	}
	
	public List<Currency> getCurrencyValues(Document document) {
		extractDateOfDocument(document);
		List<Currency> result = new ArrayList<Currency>();
		NodeList entries = document.getElementsByTagName("pozycja");
		for(int i = 0; i < entries.getLength(); ++i)
		{
			BigDecimal divider = new BigDecimal(1);
			BigDecimal value = new BigDecimal(0);
			String code = "";
			if(entries.item(i).getNodeType() == Node.ELEMENT_NODE)
			{
				Node child = entries.item(i).getFirstChild();
				while(child != null)
				{
					switch(child.getNodeName())
					{
					case "przelicznik":
					{
						divider = new BigDecimal(child.getFirstChild().getNodeValue());
						break;
					}
					case "kod_waluty":
					{
						code = child.getFirstChild().getNodeValue();
						break;
					}
					case "kurs_sredni":
					{
						value = new BigDecimal(child.getFirstChild().getNodeValue().replace(",", "."));
						break;
					}
					}
					child = child.getNextSibling();
				}
				Currency currency = newCurrency();
				value = value.divide(divider);
				currency.setCurrencyCode(code);
				currency.setValue(value);
				result.add(currency);
			} else
			{
				onError(entries.item(i).toString() + " should be ELEMENT_NODE");
			}
		}
		return result;
	}

}
