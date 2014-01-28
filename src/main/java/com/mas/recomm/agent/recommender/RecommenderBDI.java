package com.mas.recomm.agent.recommender;

import java.sql.ResultSet;
import java.util.List;

import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import com.mas.recomm.agent.common.DataMiningGoal;
import com.mas.recomm.agent.common.RecommendationBuildGoal;
import com.mas.recomm.agent.common.RecommendationGoal;
import com.mas.recomm.agent.common.SearchGoal;
import com.mas.recomm.agent.datamining.IDataMiningService;
import com.mas.recomm.agent.recommbuilder.IRecommendationBuildService;
import com.mas.recomm.agent.search.ISearchService;
import com.mas.recomm.model.RecommendedMovieItem;

import jadex.bdiv3.BDIAgent;
import jadex.bdiv3.annotation.Belief;
import jadex.bdiv3.annotation.Body;
import jadex.bdiv3.annotation.Goal;
import jadex.bdiv3.annotation.Goals;
import jadex.bdiv3.annotation.Plan;
import jadex.bdiv3.annotation.PlanAPI;
import jadex.bdiv3.annotation.PlanBody;
import jadex.bdiv3.annotation.Plans;
import jadex.bdiv3.annotation.Publish;
import jadex.bdiv3.annotation.ServicePlan;
import jadex.bdiv3.annotation.Trigger;
import jadex.bdiv3.runtime.IPlan;
import jadex.bridge.service.RequiredServiceInfo;
import jadex.bridge.service.annotation.Service;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentCreated;
import jadex.micro.annotation.Binding;
import jadex.micro.annotation.Description;
import jadex.micro.annotation.RequiredService;
import jadex.micro.annotation.RequiredServices;

@Agent
@Description("The RecommenderBDI Agent, control the whole process of recommendation.")
@Service
@RequiredServices({
	@RequiredService(name="data-mining-service", type=IDataMiningService.class, binding=@Binding(scope=RequiredServiceInfo.SCOPE_PLATFORM)),
	@RequiredService(name="search-service", type=ISearchService.class, binding=@Binding(scope=RequiredServiceInfo.SCOPE_PLATFORM)),
	@RequiredService(name="recommendation-build-service", type=IRecommendationBuildService.class, binding=@Binding(scope=RequiredServiceInfo.SCOPE_PLATFORM))
})
@Goals({
	@Goal(clazz=RecommendationGoal.class, publish=@Publish(type=IRecommendService.class)),
	@Goal(clazz=DataMiningGoal.class),
	@Goal(clazz=SearchGoal.class),
	@Goal(clazz=RecommendationBuildGoal.class)
})
@Plans({
	@Plan(trigger=@Trigger(goals=DataMiningGoal.class), body=@Body(service=@ServicePlan(name="data-mining-service"))),
	@Plan(trigger=@Trigger(goals=SearchGoal.class), body=@Body(service=@ServicePlan(name="search-service"))),
	@Plan(trigger=@Trigger(goals=RecommendationBuildGoal.class), body=@Body(service=@ServicePlan(name="recommendation-build-service")))
})
public class RecommenderBDI {
	@Agent
	BDIAgent agent;
	@Belief
	protected List<RecommendedMovieItem> recommendedList;
	@Belief
	protected List<RecommendedItem> miningRes;

	@AgentCreated
	public void init() {

	}
	
	@Plan(trigger=@Trigger(goals=RecommendationGoal.class))
	public class RecommendPlan {
		@PlanAPI
		IPlan plan;
		
		@PlanBody
		public List<RecommendedMovieItem> generateRecommendations(String userid) {
			List<RecommendedItem> miningRes = (List<RecommendedItem>) plan.dispatchSubgoal(new DataMiningGoal(userid)).get();
			List<ResultSet> searchRes = (List<ResultSet>) plan.dispatchSubgoal(new SearchGoal(miningRes)).get();
			List<RecommendedMovieItem> buildRes = (List<RecommendedMovieItem>) plan.dispatchSubgoal(new RecommendationBuildGoal(new List[]{miningRes, searchRes})).get();
			return buildRes;
		}		
	}


}
