package com.mas.recomm.agent.datamining;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.model.jdbc.ConnectionPoolDataSource;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericRecommendedItem;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.AveragingPreferenceInferrer;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import com.mas.recomm.agent.common.DataMiningItemCFGoal;
import com.mas.recomm.agent.common.DataMiningUserCFGoal;
import com.mas.recomm.agent.common.StrategyGoal;
import com.mas.recomm.agent.strategy.IStrategyService;
import com.mas.recomm.model.DataSourceFactory;
import com.mas.recomm.model.MovieRatingsDataModel;

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
import jadex.bridge.service.RequiredServiceInfo;
import jadex.bridge.service.annotation.Service;
import jadex.commons.future.Future;
import jadex.commons.future.IFuture;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentCreated;
import jadex.micro.annotation.Binding;
import jadex.micro.annotation.Description;
import jadex.micro.annotation.ProvidedService;
import jadex.micro.annotation.ProvidedServices;
import jadex.micro.annotation.RequiredService;
import jadex.micro.annotation.RequiredServices;

@Agent
@Description("DataMiningBDI Agent, provide mining recommendations service.")
@Service
@ProvidedServices(@ProvidedService(type=IDataMiningService.class))
@RequiredServices({@RequiredService(name="strategy-service", type=IStrategyService.class, binding=@Binding(scope= RequiredServiceInfo.SCOPE_PLATFORM)),
		@RequiredService(name="item-cf-service", type=IItemCFService.class, binding=@Binding(scope=RequiredServiceInfo.SCOPE_PLATFORM)),
		@RequiredService(name="user-cf-service", type=IUserCFService.class, binding=@Binding(scope=RequiredServiceInfo.SCOPE_PLATFORM))})
@Goals({@Goal(clazz=StrategyGoal.class), @Goal(clazz=DataMiningUserCFGoal.class)})
@Plans({@Plan(trigger=@Trigger(goals=StrategyGoal.class), body=@Body(service=@ServicePlan(name="strategy-service"))), 
		@Plan(trigger=@Trigger(goals=DataMiningUserCFGoal.class), body=@Body(service=@ServicePlan(name="user-cf-service"))),
		@Plan(trigger=@Trigger(goals=DataMiningItemCFGoal.class), body=@Body(service=@ServicePlan(name="item-cf-service")))})
public class DataMiningBDI implements IDataMiningService{
	@Agent
	BDIAgent agent;
	
	@AgentCreated
	public void init() {
		
	}
	
	@Override
	public IFuture<List<RecommendedItem>> mineRecommendations(String userid) {
		System.out.println("miningRecommendations start");
		String strategy = (String) agent.dispatchTopLevelGoal(new StrategyGoal(userid)).get();
		List<RecommendedItem> miningRes = null;
	
		if (strategy.equals(StrategyGoal.STRATEGY_CF_USER)) {
			miningRes = (List<RecommendedItem>) agent.dispatchTopLevelGoal(new DataMiningUserCFGoal(new String[]{userid, strategy})).get();
		} else {
			miningRes = (List<RecommendedItem>) agent.dispatchTopLevelGoal(new DataMiningItemCFGoal(new String[]{userid, strategy})).get();
		}
		
		System.out.println("miningRecommendations end " + miningRes);
		return new Future<List<RecommendedItem>>(miningRes);
	}

}
