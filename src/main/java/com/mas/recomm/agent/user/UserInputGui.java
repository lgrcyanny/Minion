package com.mas.recomm.agent.user;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import jadex.bdiv3.BDIAgent;
import jadex.bridge.IExternalAccess;
import jadex.bridge.service.RequiredServiceInfo;
import jadex.bridge.service.search.SServiceProvider;
import jadex.commons.future.DefaultResultListener;
import jadex.commons.future.IFuture;
import jadex.commons.future.IntermediateDefaultResultListener;
import jadex.commons.future.IntermediateFuture;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class UserInputGui extends JFrame{
	private BDIAgent agent;
	private JTextField useridTextField;
	private JTextArea resultTextArea;
	private JButton recommendBtn;
	
	public UserInputGui(BDIAgent agent) {
		super("Multi Agent Recommendation Engine");
		this.agent = agent;
		this.initGui();
		this.addWindowCloseListener();
		//this.pack();
		this.setVisible(true);
	}
	
	private void initGui() {
		this.setLocation(400, 100);
		this.setSize(600, 500);
		//this.setPreferredSize(new Dimension(500, 500));
		useridTextField = new JTextField("1");
		resultTextArea = new JTextArea(20, 20);
		resultTextArea.setEditable(false);
		recommendBtn = new JButton("Generate Recommendations");
		this.setLayout(new BorderLayout());
		JPanel panel = new JPanel(new BorderLayout());
		JLabel label = new JLabel("<html><body><p style=\"margin: 5px\">UserID</p></body></html>");
		panel.add(label, BorderLayout.WEST);		
		panel.add(useridTextField, BorderLayout.CENTER);
		getContentPane().add(panel, BorderLayout.NORTH);		
		getContentPane().add(new JScrollPane(resultTextArea), BorderLayout.CENTER);
		getContentPane().add(recommendBtn, BorderLayout.SOUTH);
	}
	
	private void addRecommendBtnListener() {
		
	}
	
	private void addWindowCloseListener () {
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				agent.killComponent();
			}
			
		});
		
	}
}
