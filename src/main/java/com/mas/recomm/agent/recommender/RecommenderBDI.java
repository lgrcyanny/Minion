package com.mas.recomm.agent.recommender;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.AveragingPreferenceInferrer;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import com.mas.recomm.agent.common.DataMiningUserCFGoal;
import com.mas.recomm.agent.common.RecommendationBuildGoal;
import com.mas.recomm.agent.common.RecommendationGoal;
import com.mas.recomm.agent.common.SearchGoal;
import com.mas.recomm.agent.datamining.IDataMiningService;
import com.mas.recomm.agent.recommbuilder.IRecommendationBuildService;
import com.mas.recomm.agent.search.ISearchService;
import com.mas.recomm.model.MovieRatingsDataModel;
import com.mas.recomm.model.RecommendedMovieItem;

import jadex.bdiv3.BDIAgent;
import jadex.bdiv3.annotation.Belief;
import jadex.bdiv3.annotation.Body;
import jadex.bdiv3.annotation.Goal;
import jadex.bdiv3.annotation.Goals;
import jadex.bdiv3.annotation.Plan;
import jadex.bdiv3.annotation.Plans;
import jadex.bdiv3.annotation.Publish;
import jadex.bdiv3.annotation.ServicePlan;
import jadex.bdiv3.annotation.Trigger;
import jadex.bridge.IComponentStep;
import jadex.bridge.IInternalAccess;
import jadex.bridge.service.RequiredServiceInfo;
import jadex.bridge.service.annotation.Service;
import jadex.bridge.service.search.SServiceProvider;
import jadex.commons.future.DefaultResultListener;
import jadex.commons.future.Future;
import jadex.commons.future.IFuture;
import jadex.commons.future.IResultListener;
import jadex.commons.future.IntermediateDefaultResultListener;
import jadex.commons.future.IntermediateFuture;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentCreated;
import jadex.micro.annotation.Binding;
import jadex.micro.annotation.Description;
import jadex.micro.annotation.ProvidedService;
import jadex.micro.annotation.ProvidedServices;
import jadex.micro.annotation.RequiredService;
import jadex.micro.annotation.RequiredServices;

@Agent
@Description("The RecommenderBDI Agent, control the whole process of recommendation.")
@Service
@ProvidedServices(@ProvidedService(type = IRecommendService.class))
public class RecommenderBDI implements IRecommendService {
	@Agent
	BDIAgent agent;
	@Belief
	protected List<RecommendedMovieItem> recommendedList;
	@Belief
	protected List<RecommendedItem> miningRes;

	@AgentCreated
	public void init() {

	}

	@SuppressWarnings("unchecked")
	@Override
	public IFuture<List<RecommendedMovieItem>> generateRecommendations(
			final String userid) {
		System.out.println("generateRecommendations start");
		final Future<List<RecommendedMovieItem>> ret = new Future<List<RecommendedMovieItem>>();
		// Find Service
		final IntermediateFuture<IDataMiningService> dataMiningService = (IntermediateFuture<IDataMiningService>) SServiceProvider
				.getServices(agent.getServiceProvider(),
						IDataMiningService.class,
						RequiredServiceInfo.SCOPE_PLATFORM);
		final IntermediateFuture<ISearchService> searchService = (IntermediateFuture<ISearchService>) SServiceProvider
				.getServices(agent.getServiceProvider(),
						ISearchService.class,
						RequiredServiceInfo.SCOPE_PLATFORM);
		final IntermediateFuture<IRecommendationBuildService> recommBuildService = (IntermediateFuture<IRecommendationBuildService>) SServiceProvider
				.getServices(agent.getServiceProvider(),
						IRecommendationBuildService.class,
						RequiredServiceInfo.SCOPE_PLATFORM);
	
		// Invoke Data Mining Service
		dataMiningService.addResultListener(new IntermediateDefaultResultListener<IDataMiningService>() {

			@Override
			public void intermediateResultAvailable(IDataMiningService imMiningService) {
				IFuture<List<RecommendedItem>> futMiningRes = imMiningService.mineRecommendations(userid);
				futMiningRes.addResultListener(new DefaultResultListener<List<RecommendedItem>>() {

					@Override
					public void resultAvailable(final List<RecommendedItem> miningRes) {
						//System.out.println("Mining done " + miningRes);
						// After datamining done, invoke search service
						searchService.addResultListener(new IntermediateDefaultResultListener<ISearchService>() {

							@Override
							public void intermediateResultAvailable(
									ISearchService imSearchService) {
								IFuture<List<ResultSet>> futSearchRes = imSearchService.searchRecommendedDetails(miningRes);
								futSearchRes.addResultListener(new DefaultResultListener<List<ResultSet>>() {

									@Override
									public void resultAvailable(final List<ResultSet> searchRes) {
										// After search done, invoke recommendation build service
										recommBuildService.addResultListener(new IntermediateDefaultResultListener<IRecommendationBuildService>() {

											@Override
											public void intermediateResultAvailable(
													IRecommendationBuildService imBuildService) {
												IFuture<List<RecommendedMovieItem>> futBuildRes = imBuildService.buildRecommendationRes(miningRes, searchRes);
												futBuildRes.addResultListener(new DefaultResultListener<List<RecommendedMovieItem>>() {

													@Override
													public void resultAvailable(
															List<RecommendedMovieItem> buildRes) {
														// After build done, set the final result
														ret.setResult(buildRes);
													}
												});
											}
										});
									}
								});
							}
						});
					}
				});
			}
		});

		System.out.println("generateRecommendations end");
		return ret;
	}

}
