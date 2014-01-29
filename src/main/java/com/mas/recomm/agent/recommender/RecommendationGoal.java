package com.mas.recomm.agent.recommender;

import java.util.List;

import com.mas.recomm.model.RecommendedMovieItem;
import jadex.bdiv3.annotation.Goal;
import jadex.bdiv3.annotation.GoalParameter;
import jadex.bdiv3.annotation.GoalResult;

@Goal
public class RecommendationGoal {
	@GoalParameter
	protected String userid; // GoalParameter must be Object, not int
	
	@GoalResult
	protected List<RecommendedMovieItem> reslist;
	
	public RecommendationGoal(String userid) {
		this.userid = userid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public List<RecommendedMovieItem> getReslist() {
		return reslist;
	}

	public void setReslist(List<RecommendedMovieItem> reslist) {
		this.reslist = reslist;
	}
}
