package com.mas.recomm.agent.user;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.List;

import jadex.bdiv3.BDIAgent;
import jadex.bridge.IComponentStep;
import jadex.bridge.IInternalAccess;
import jadex.bridge.service.RequiredServiceInfo;
import jadex.bridge.service.search.SServiceProvider;
import jadex.commons.future.DefaultResultListener;
import jadex.commons.future.Future;
import jadex.commons.future.IFuture;
import jadex.commons.future.IResultListener;
import jadex.commons.future.IntermediateDefaultResultListener;
import jadex.commons.future.IntermediateFuture;
import jadex.platform.service.cms.IntermediateResultListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import com.ibm.icu.util.TimeUnit;
import com.mas.recomm.agent.common.DataMiningGoal;
import com.mas.recomm.agent.common.RecommendationGoal;
import com.mas.recomm.agent.datamining.IDataMiningService;
import com.mas.recomm.agent.recommender.IRecommendService;
import com.mas.recomm.model.RecommendedMovieItem;


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
		this.addRecommendBtnListener();
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
		recommendBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				final String userid = useridTextField.getText();
				resultTextArea.setText("Processing, please wait...");
				agent.scheduleStep(new IComponentStep<Void>() {

					@Override
					public IFuture<Void> execute(IInternalAccess ia) {
						System.out.println("userid" + userid);
						IntermediateFuture<IRecommendService> service = (IntermediateFuture<IRecommendService>) SServiceProvider.getServices(agent.getServiceProvider(), IRecommendService.class, RequiredServiceInfo.SCOPE_PLATFORM);
						service.addResultListener(new IntermediateDefaultResultListener<IRecommendService>() {

							@Override
							public void intermediateResultAvailable(
									IRecommendService imService) {
								IFuture<List<RecommendedMovieItem>> futRes = imService.generateRecommendations(userid);
								futRes.addResultListener(new DefaultResultListener<List<RecommendedMovieItem>>() {

									@Override
									public void resultAvailable(
											final List<RecommendedMovieItem> result) {
										SwingUtilities.invokeLater(new Runnable() {
											
											@Override
											public void run() {
												resultTextArea.setText("Recommended Movies:\n");
												Iterator<RecommendedMovieItem> iterator = result.iterator();
												while(iterator.hasNext()) {
													RecommendedMovieItem item = iterator.next();
													String displayStr = item.getId() + "-" + item.getTitle() + "-" + item.getGenres() + "-" + item.getScore() + "\n";
													resultTextArea.append(displayStr);
												}
											}
										});
										
									}
								});
							}
						});
						
						return IFuture.DONE;
					}
				
				});	
				
			}
			
			
		});
		
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
