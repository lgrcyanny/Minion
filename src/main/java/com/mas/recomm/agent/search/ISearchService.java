package com.mas.recomm.agent.search;

import jadex.commons.future.IFuture;

import java.sql.ResultSet;
import java.util.List;

import org.apache.mahout.cf.taste.recommender.RecommendedItem;

public interface ISearchService {
	
	public IFuture<List<ResultSet>> searchRecommendedDetails(List<RecommendedItem> miningRes);

}
