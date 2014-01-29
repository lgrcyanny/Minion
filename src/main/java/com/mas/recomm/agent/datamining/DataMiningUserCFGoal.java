package com.mas.recomm.agent.datamining;

import java.util.List;

import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import jadex.bdiv3.annotation.Goal;
import jadex.bdiv3.annotation.GoalParameter;
import jadex.bdiv3.annotation.GoalResult;

@Goal
public class DataMiningUserCFGoal{
	public static final int HOMW_MANY = 20;
	public static final int NEAREST_NEIGHBOUR = 5;

	@GoalParameter
	protected String[] params;// userid = params[0], strategy = params[1]

	@GoalResult
	protected List<RecommendedItem> miningRes;

	public DataMiningUserCFGoal(String[] params) {
		this.params = params;
	}

	public String[] getParams() {
		return params;
	}

	public void setParams(String[] params) {
		this.params = params;
	}

	public List<RecommendedItem> getMiningRes() {
		return miningRes;
	}

	public void setMiningRes(List<RecommendedItem> miningRes) {
		this.miningRes = miningRes;
	}
}
