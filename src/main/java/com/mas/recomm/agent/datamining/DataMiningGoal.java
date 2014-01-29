package com.mas.recomm.agent.datamining;

import java.util.List;

import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import jadex.bdiv3.annotation.Goal;
import jadex.bdiv3.annotation.GoalParameter;
import jadex.bdiv3.annotation.GoalResult;

@Goal
public class DataMiningGoal {
	@GoalParameter
	protected String userid;

	@GoalResult
	protected List<RecommendedItem> miningRes;

	public DataMiningGoal(String userid) {
		this.userid = userid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public List<RecommendedItem> getMiningRes() {
		return miningRes;
	}

	public void setMiningRes(List<RecommendedItem> miningRes) {
		this.miningRes = miningRes;
	}
}
