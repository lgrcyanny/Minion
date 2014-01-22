package com.mas.recomm.agent.common;

import java.util.List;

import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import jadex.bdiv3.annotation.Goal;
import jadex.bdiv3.annotation.GoalParameter;
import jadex.bdiv3.annotation.GoalResult;

@Goal
public class DataMiningGoal {
	@GoalParameter
	private int userid;
	@GoalResult
	protected List<RecommendedItem> mahoutRecommRes;
	
	public DataMiningGoal(int userid) {
		this.userid = userid;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public List<RecommendedItem> getMahoutRecommRes() {
		return mahoutRecommRes;
	}

	public void setMahoutRecommRes(List<RecommendedItem> mahoutRecommRes) {
		this.mahoutRecommRes = mahoutRecommRes;
	}
}
