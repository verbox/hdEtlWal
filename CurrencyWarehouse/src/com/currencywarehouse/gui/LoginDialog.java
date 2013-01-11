package com.currencywarehouse.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2061742736144977753L;
	private final JPanel contentPanel = new JPanel();
	private JTextField eLogin;
	private JPasswordField ePassword;
	private String login, password;

	/**
	 * Create the dialog.
	 */
	public LoginDialog() {
		setTitle("Database login prompt");
		setBounds(100, 100, 509, 115);
		getContentPane().setLayout(new BorderLayout());
		FlowLayout fl_contentPanel = new FlowLayout();
		contentPanel.setLayout(fl_contentPanel);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JLabel lblNewLabel = new JLabel("Login");
			contentPanel.add(lblNewLabel);
		}
		{
			eLogin = new JTextField();
			eLogin.setPreferredSize(new Dimension(150, 20));
			contentPanel.add(eLogin);
			eLogin.setColumns(10);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Password");
			contentPanel.add(lblNewLabel_1);
		}
		{
			ePassword = new JPasswordField();
			ePassword.setPreferredSize(new Dimension(150, 20));
			contentPanel.add(ePassword);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						login = eLogin.getText();
						password = new String(ePassword.getPassword());
						setVisible(false);
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}
	
	

}
