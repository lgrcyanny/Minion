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

import com.mas.recomm.agent.common.DataMiningGoal;
import com.mas.recomm.model.DataSourceFactory;
import com.mas.recomm.model.MovieRatingsDataModel;

import jadex.bdiv3.BDIAgent;
import jadex.bdiv3.annotation.Belief;
import jadex.bdiv3.annotation.Goal;
import jadex.bdiv3.annotation.Goals;
import jadex.bdiv3.annotation.Plan;
import jadex.bdiv3.annotation.Publish;
import jadex.bdiv3.annotation.Trigger;
import jadex.bridge.service.annotation.Service;
import jadex.commons.future.Future;
import jadex.commons.future.IFuture;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentCreated;
import jadex.micro.annotation.Description;
import jadex.micro.annotation.ProvidedService;
import jadex.micro.annotation.ProvidedServices;

@Agent
@Description("DataMiningBDI Agent, provide mining recommendations service.")
@Service
@ProvidedServices(@ProvidedService(type=IDataMiningService.class))
public class DataMiningBDI implements IDataMiningService{
	@Agent
	BDIAgent agent;
	
	@AgentCreated
	public void init() {
		
	}
	
	@Override
	public IFuture<List<RecommendedItem>> mineRecommendations(String userid) {
		System.out.println("miningRecommendations start");
		List<RecommendedItem> miningRes = new ArrayList<RecommendedItem>();
		try {
			DataModel model = new FileDataModel(new File("src/main/resources/ratings.dat"));
			//DataModel model = new MovieRatingsDataModel();
			UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
			similarity.setPreferenceInferrer(new AveragingPreferenceInferrer(model));
			UserNeighborhood neighborhood = new NearestNUserNeighborhood(3, similarity, model);
			Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
			CachingRecommender cachingRecommender = new CachingRecommender(recommender);
			List<RecommendedItem> result = cachingRecommender.recommend(Integer.parseInt(userid), 10);
			// If don't create GenericRecommendedItem, the return result will have zero ID, and value, I dont know why
			for (Iterator<RecommendedItem> iterator = result.iterator(); iterator.hasNext(); ) {
				RecommendedItem item = iterator.next();
				miningRes.add(new GenericRecommendedItem(item.getItemID(), item.getValue()));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("miningRecommendations end " + miningRes);
		return new Future<List<RecommendedItem>>(miningRes);
	}

}
