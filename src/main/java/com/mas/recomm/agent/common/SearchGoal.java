package com.mas.recomm.agent.common;

import java.sql.ResultSet;
import java.util.List;

import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import jadex.bdiv3.annotation.Goal;
import jadex.bdiv3.annotation.GoalParameter;
import jadex.bdiv3.annotation.GoalResult;

@Goal
public class SearchGoal {
	@GoalParameter
	protected List<RecommendedItem> mahoutRes;
	@GoalResult
	protected ResultSet recommDetails;
	
	public SearchGoal(List<RecommendedItem> mahoutRes) {
		this.mahoutRes = mahoutRes;
	}

	public List<RecommendedItem> getMahoutRes() {
		return mahoutRes;
	}

	public void setMahoutRes(List<RecommendedItem> mahoutRes) {
		this.mahoutRes = mahoutRes;
	}

	public ResultSet getRecommDetails() {
		return recommDetails;
	}

	public void setRecommDetails(ResultSet recommDetails) {
		this.recommDetails = recommDetails;
	}
		
}
