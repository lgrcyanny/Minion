package com.mas.recomm.agent.recommbuilder;

import jadex.bdiv3.annotation.Goal;
import jadex.bdiv3.annotation.GoalParameter;
import jadex.bdiv3.annotation.GoalResult;

import java.sql.ResultSet;
import java.util.List;

import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import com.mas.recomm.model.RecommendedMovieItem;

@Goal
public class RecommendationBuildGoal {
	@GoalParameter
	protected List[] params;

	@GoalResult
	protected List<RecommendedMovieItem> recommList;

	public RecommendationBuildGoal(List[] params) {
		this.params = params;
	}

	public List[] getParams() {
		return params;
	}

	public void setParams(List[] params) {
		this.params = params;
	}

	public List<RecommendedMovieItem> getRecommList() {
		return recommList;
	}

	public void setRecommList(List<RecommendedMovieItem> recommList) {
		this.recommList = recommList;
	}
}
