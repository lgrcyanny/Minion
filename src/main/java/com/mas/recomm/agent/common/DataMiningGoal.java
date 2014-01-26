package com.mas.recomm.agent.common;

import java.util.List;

import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import jadex.bdiv3.annotation.Goal;
import jadex.bdiv3.annotation.GoalParameter;
import jadex.bdiv3.annotation.GoalResult;

@Goal
public class DataMiningGoal {
	@GoalParameter
	private String userid;
	@GoalResult
	protected String miningRes;
	
	public DataMiningGoal(String userid) {
		this.userid = userid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getMiningRes() {
		return miningRes;
	}

	public void setMiningRes(String miningRes) {
		this.miningRes = miningRes;
	}
	
}
