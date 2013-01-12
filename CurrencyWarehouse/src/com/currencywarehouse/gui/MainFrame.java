package com.currencywarehouse.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.SwingWorker;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;
import java.sql.Connection;
import java.util.List;

import javax.swing.Box;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import com.currencywarehouse.data.ErrorListener;
import com.currencywarehouse.data.SQLConnectionFactory;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3963727338601934118L;
	private JPanel contentPane;
	private LoginDialog loginDialog;
	
	private JTextArea textAreaOutput;
	private JLabel lblConnectionState;
	private JButton btnConnect;
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
		loginDialog = new LoginDialog();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 641, 477);
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
	}
	
	public void clickConnectButton(ActionEvent arg0) {
		//i will use SwingWorker class - it automatically works with EDT, threads etc.
		SwingWorker<Connection,String> sw = new SwingWorker<Connection,String>() {

			@Override
			protected Connection doInBackground() throws Exception {
				btnConnect.setEnabled(false);
				publish("Connection progress...");
				SQLConnectionFactory.setEl(new TextAreaErrorListener(textAreaOutput));
				Connection con = SQLConnectionFactory.createConnection();
				if (con != null) {
					publish("Connection succesfull!\n");
					lblConnectionState.setText("<html><font color=green><b>OK</b></font></html>");
				} else {
					publish("Connection error! Please read error messages above.\n");
					lblConnectionState.setText("<html><font color=red><b>Not connected</b></font></html>");
				}
				btnConnect.setEnabled(true);
				return con;
			}
			
			@Override
			protected void process(List<String> chunks) {
				for(String str : chunks) {
					textAreaOutput.append(str);
				}
			}
			
		};
		sw.execute();
	}
	

}
