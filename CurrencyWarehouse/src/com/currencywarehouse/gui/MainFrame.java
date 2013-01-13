package com.currencywarehouse.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.AbstractButton;
import javax.swing.JLabel;
import javax.swing.SwingWorker;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;
import java.sql.Connection;
import java.util.Enumeration;
import java.util.List;

import javax.swing.Box;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import com.currencywarehouse.data.AbstractErrorReporter;
import com.currencywarehouse.data.DataInserter;
import com.currencywarehouse.data.DataLoader;
import com.currencywarehouse.data.ETLComponentFactory;
import com.currencywarehouse.data.SQLConnectionFactory;
import com.currencywarehouse.entities.Currency;

import javax.swing.JRadioButton;
import java.awt.FlowLayout;
import javax.swing.ButtonGroup;
import javax.swing.JProgressBar;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3963727338601934118L;
	private JPanel contentPane;
	
	private JTextArea textAreaOutput;
	private JLabel lblConnectionState;
	private JButton btnConnect;
	private JButton btnETL;
	private JButton btnMemory;
	private JLabel lblProgress;
	
	private TextAreaErrorListener taerrorListener;
	
	private JProgressBar progressBar;
	private JRadioButton rdbtnOracleDatabase;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	
	private SWorkerETL sw;
	private CurrenciesFrame cf;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		//construct children
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 775, 477);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Connection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clickConnectButton(arg0);
			}
		});
		panel.add(btnConnect);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		panel.add(horizontalStrut);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		panel.add(horizontalStrut_1);
		
		JLabel lblLabelConnectionState = new JLabel("Connection state:");
		panel.add(lblLabelConnectionState);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		panel.add(horizontalStrut_2);
		
		lblConnectionState = new JLabel("<html><font color=red><b>Not connected</b></font></html>");
		panel.add(lblConnectionState);
		
		JScrollPane scrollPaneOutput = new JScrollPane();
		scrollPaneOutput.setViewportBorder(new TitledBorder(null, "Output", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(scrollPaneOutput, BorderLayout.SOUTH);
		
		textAreaOutput = new JTextArea();
		textAreaOutput.setEditable(false);
		textAreaOutput.setLineWrap(true);
		textAreaOutput.setRows(10);
		scrollPaneOutput.setViewportView(textAreaOutput);
		
		JPanel middlePanel = new JPanel();
		contentPane.add(middlePanel, BorderLayout.CENTER);
		middlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		Box verticalBox_5 = Box.createVerticalBox();
		middlePanel.add(verticalBox_5);
		
		progressBar = new JProgressBar();
		btnETL = new JButton("Extract, Transform and Load");

		verticalBox_5.add(btnETL);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		verticalBox_5.add(verticalStrut_1);
		
		lblProgress = new JLabel("Progress:");
		verticalBox_5.add(lblProgress);
		
		
		verticalBox_5.add(progressBar);
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		verticalBox_5.add(verticalStrut_2);
		//LEFT PANEL
		JPanel leftPanel = new JPanel();
		leftPanel.setBorder(new TitledBorder(null, "Source", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(leftPanel, BorderLayout.WEST);
		leftPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		Box verticalBox_1 = Box.createVerticalBox();
		leftPanel.add(verticalBox_1);
		
		Box verticalBox = Box.createVerticalBox();
		verticalBox_1.add(verticalBox);
		
		JRadioButton rdbtnXLSFilesS = new JRadioButton("XLS Files (NBP)");
		rdbtnXLSFilesS.setSelected(true);
		buttonGroup.add(rdbtnXLSFilesS);
		verticalBox.add(rdbtnXLSFilesS);
		
		JRadioButton rdbtnXMLFilesS = new JRadioButton("XML Files (NBP)");
		rdbtnXMLFilesS.setSelected(true);
		buttonGroup.add(rdbtnXMLFilesS);
		verticalBox.add(rdbtnXMLFilesS);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		verticalBox_1.add(verticalStrut);
		
		Box verticalBox_2 = Box.createVerticalBox();
		verticalBox_1.add(verticalBox_2);
		
		JPanel rightPanel = new JPanel();
		rightPanel.setBorder(new TitledBorder(null, "Destination", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(rightPanel, BorderLayout.EAST);
		
		Box verticalBox_4 = Box.createVerticalBox();
		rightPanel.add(verticalBox_4);
		
		Box verticalBox_3 = Box.createVerticalBox();
		verticalBox_4.add(verticalBox_3);
		
		JRadioButton rdbtnMemory = new JRadioButton("Memory");
		rdbtnMemory.setSelected(true);
		buttonGroup_1.add(rdbtnMemory);
		verticalBox_3.add(rdbtnMemory);
		
		rdbtnOracleDatabase = new JRadioButton("Oracle Database");
		buttonGroup_1.add(rdbtnOracleDatabase);
		rdbtnOracleDatabase.setEnabled(false);
		verticalBox_3.add(rdbtnOracleDatabase);
		
		Component verticalStrut_3 = Box.createVerticalStrut(20);
		verticalBox_3.add(verticalStrut_3);
		
		btnMemory = new JButton("Memory");

		btnMemory.setEnabled(false);
		btnMemory.setVisible(false);
		verticalBox_3.add(btnMemory);
		
		//actions
		btnETL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resetETLWorker();
				clickETLButton(arg0);
			}
		});
		
		btnMemory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (cf!=null) {
					cf.setVisible(true);
				}
			}
		});
		
		//error listener
		taerrorListener = new TextAreaErrorListener(textAreaOutput);
	}
	
	public void clickConnectButton(ActionEvent arg0) {
		//i will use SwingWorker class - it automatically works with EDT, threads etc.
		SwingWorker<Connection,String> swc = new SwingWorker<Connection,String>() {

			@Override
			protected Connection doInBackground() throws Exception {
				btnConnect.setEnabled(false);
				publish("Connection progress...");
				SQLConnectionFactory.setErrorListener(taerrorListener);
				Connection con = SQLConnectionFactory.createConnection();
				if (con != null) {
					publish("Connection succesfull!");
					lblConnectionState.setText("<html><font color=green><b>OK</b></font></html>");
					rdbtnOracleDatabase.setEnabled(true);
				} else {
					publish("Connection error! Please read error messages above.");
					lblConnectionState.setText("<html><font color=red><b>Not connected</b></font></html>");
				}
				btnConnect.setEnabled(true);
				return con;
			}
			
			@Override
			protected void process(List<String> chunks) {
				for(String str : chunks) {
					addMessageToOutput(str);
				}
			}
			
		};
		swc.execute();
		
	}
	
	public void clickETLButton(ActionEvent arg0) {
		final DataLoader dl = ETLComponentFactory.getLoader(getSelectedButtonId(buttonGroup));
		final DataInserter di = ETLComponentFactory.getInserter(getSelectedButtonId(buttonGroup_1));
		try {
			AbstractErrorReporter errorReporter = (AbstractErrorReporter) dl;
			errorReporter.addErrorListener(taerrorListener);
		}
		catch (ClassCastException ex) {
			addMessageToOutput(ex.getMessage());
		}
		sw.setETLElements(dl, di);
		sw.execute();
	}
	
	public int getSelectedButtonId(ButtonGroup bg) {
		int number = -1;
		for (Enumeration<AbstractButton> buttons = bg.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            number++;
            if (button.isSelected()) {
                return number;
            }
        }
		return -1;
	}
	
	public synchronized void addMessageToOutput(String str) {
		textAreaOutput.append(str+"\n");
	}
	
	public void resetETLWorker() {
		sw = new SWorkerETL(this,btnETL,lblProgress,progressBar);
	}
	
	public void prepareListOfCurrenciesFrame(List<Currency> list) {
		if (list!=null) {
			cf = new CurrenciesFrame(list);
			btnMemory.setVisible(true);
			btnMemory.setEnabled(true);
		}
	}
	
}
