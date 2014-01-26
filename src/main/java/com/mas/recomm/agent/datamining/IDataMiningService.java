package com.mas.recomm.agent.datamining;

import java.util.List;

import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import jadex.commons.future.IFuture;

public interface IDataMiningService {
	
	public IFuture<List<RecommendedItem>> mineRecommendations(String userid);

}
