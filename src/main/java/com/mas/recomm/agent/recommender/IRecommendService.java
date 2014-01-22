package com.mas.recomm.agent.recommender;

import java.util.List;

import com.mas.recomm.model.RecommendedMovieItem;

import jadex.commons.future.IFuture;

/**
 * The recommendation service provided by recommender agent
 * @author lgrcyanny
 *
 */
public interface IRecommendService {
	
	public IFuture<List<RecommendedMovieItem>> generateRecommendations(int userid); 

}
