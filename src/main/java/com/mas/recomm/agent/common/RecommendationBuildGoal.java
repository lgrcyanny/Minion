package com.mas.recomm.agent.common;

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
	protected List<RecommendedItem> mahoutRes;
	@GoalParameter
	protected ResultSet recommDetails;
	@GoalResult
	protected List<RecommendedMovieItem> recommList;
	
	public RecommendationBuildGoal(List<RecommendedItem> mahoutRes, ResultSet recommDetails) {
		this.mahoutRes = mahoutRes;
		this.recommDetails = recommDetails;
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

	public List<RecommendedMovieItem> getRecommList() {
		return recommList;
	}

	public void setRecommList(List<RecommendedMovieItem> recommList) {
		this.recommList = recommList;
	}
}
