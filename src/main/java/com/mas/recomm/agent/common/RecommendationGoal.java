package com.mas.recomm.agent.common;

import java.util.List;

import com.mas.recomm.model.RecommendedMovieItem;
import jadex.bdiv3.annotation.Goal;
import jadex.bdiv3.annotation.GoalParameter;
import jadex.bdiv3.annotation.GoalResult;

@Goal
public class RecommendationGoal {
	@GoalParameter
	protected int userid;
	@GoalResult
	protected List<RecommendedMovieItem> reslist;
	
	public RecommendationGoal(int userid) {
		this.userid = userid;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public List<RecommendedMovieItem> getReslist() {
		return reslist;
	}

	public void setReslist(List<RecommendedMovieItem> reslist) {
		this.reslist = reslist;
	}
}
