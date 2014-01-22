package com.mas.recomm.agent.user;

import javax.swing.SwingUtilities;

import com.mas.recomm.agent.common.RecommendationGoal;
import com.mas.recomm.agent.recommender.IRecommendService;

import jadex.bdiv3.BDIAgent;
import jadex.bdiv3.annotation.Body;
import jadex.bdiv3.annotation.Goal;
import jadex.bdiv3.annotation.Goals;
import jadex.bdiv3.annotation.Plan;
import jadex.bdiv3.annotation.Plans;
import jadex.bdiv3.annotation.ServicePlan;
import jadex.bdiv3.annotation.Trigger;
import jadex.bridge.service.RequiredServiceInfo;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentCreated;
import jadex.micro.annotation.Binding;
import jadex.micro.annotation.Description;
import jadex.micro.annotation.RequiredService;
import jadex.micro.annotation.RequiredServices;

@Agent
@Description("The user interface Agent")
@RequiredServices(@RequiredService(name="recomm-service", type=IRecommendService.class, 
		binding=@Binding(scope=RequiredServiceInfo.SCOPE_PLATFORM)))
@Goals(@Goal(clazz=RecommendationGoal.class))
@Plans(@Plan(trigger=@Trigger(goals=RecommendationGoal.class), body=@Body(service=@ServicePlan(name="recomm-service"))))
public class UserBDI {
	@Agent
	BDIAgent agent;
	private UserInputGui gui;
	
	@AgentCreated
	public void init() {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				gui = new UserInputGui(agent);
			}
		});
	}

}
