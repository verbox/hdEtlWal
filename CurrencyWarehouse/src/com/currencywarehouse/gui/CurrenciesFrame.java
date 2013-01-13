package com.currencywarehouse.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import com.currencywarehouse.entities.Currency;

import java.awt.Dialog.ModalExclusionType;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class CurrenciesFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7353926231078592424L;
	private JPanel contentPane;
	private JTable currenciesTable;
	private List<Currency> currencies;
	
	/**
	 * Create the frame.
	 */
	public CurrenciesFrame(List<Currency> currencies) {
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		currenciesTable = new JTable();
		currenciesTable.setModel(new DefaultTableModel(convertListToObjects(currencies),
			new String[] {
				"Number", "Value", "Date", "Code"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		scrollPane.setViewportView(currenciesTable);
	}
	
	public Object[][] convertListToObjects(List<Currency> currencies) {
		Object[][] result = new Object[currencies.size()][4];
		Currency cur;
		for(int i=0; i<currencies.size(); ++i) {
			cur = currencies.get(i);
			result[i][0] = i;
			result[i][1] = cur.getCurrencyCode();
			result[i][2] = cur.getDate();
			result[i][3] = cur.getValue();
		}
		return result;
	}
	

}
