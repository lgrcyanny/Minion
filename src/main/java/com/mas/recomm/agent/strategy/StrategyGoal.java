package com.mas.recomm.agent.strategy;

import jadex.bdiv3.annotation.Goal;
import jadex.bdiv3.annotation.GoalParameter;
import jadex.bdiv3.annotation.GoalResult;

@Goal
public class StrategyGoal {
	public final static String STRATEGY_CF_ITEM = "CF_ITEM"; // Item based collaborative filtering
	public final static String STRATEGY_CF_USER = "CF_USER"; // User based collaborative filtering
	public final static double STRATEGY_EXPECT_STANDARD = 0.8; // When standard deviation less than 0.8, adopt Item CF
	@GoalParameter
	private String userid;
	@GoalResult
	private String strategy;
	
	public StrategyGoal(String userid) {
		this.userid = userid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

}
