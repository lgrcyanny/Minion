package com.mas.recomm.agent.search;

import java.sql.ResultSet;
import java.util.List;

import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import jadex.bdiv3.annotation.Goal;
import jadex.bdiv3.annotation.GoalParameter;
import jadex.bdiv3.annotation.GoalResult;

@Goal
public class SearchGoal {
	@GoalParameter
	protected List<RecommendedItem> miningRes;

	@GoalResult
	protected List<ResultSet> serchRes;

	public SearchGoal(List<RecommendedItem> mahoutRes) {
		this.miningRes = mahoutRes;
	}

	public List<RecommendedItem> getMahoutRes() {
		return miningRes;
	}

	public void setMahoutRes(List<RecommendedItem> mahoutRes) {
		this.miningRes = mahoutRes;
	}

	public List<ResultSet> getRecommDetails() {
		return serchRes;
	}

	public void setRecommDetails(List<ResultSet> recommDetails) {
		this.serchRes = recommDetails;
	}

}
