package com.mas.recomm.agent.datamining;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericRecommendedItem;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.AveragingPreferenceInferrer;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import com.mas.recomm.model.MovieRatingsDataFileModel;

import jadex.bdiv3.BDIAgent;
import jadex.bdiv3.annotation.Goal;
import jadex.bdiv3.annotation.Goals;
import jadex.bdiv3.annotation.Plan;
import jadex.bdiv3.annotation.Publish;
import jadex.bdiv3.annotation.Trigger;
import jadex.bridge.service.annotation.Service;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.Description;

@Agent
@Description("Mine Recommendatios with User CF")
@Service
@Goals(@Goal(clazz=DataMiningUserCFGoal.class, publish=@Publish(type=IUserCFService.class)))
public class UserCFBDI {
	@Agent
	BDIAgent agent;
	
	@Plan(trigger=@Trigger(goals=DataMiningUserCFGoal.class))
	public List<RecommendedItem> mineRecommendations(String[] params) {
		String userid = params[0];
		String strategy = params[1];
		System.out.println("User CF : mineRecommendations start, strategy is " + strategy + ", userid is " + userid);
		List<RecommendedItem> miningRes = new ArrayList<RecommendedItem>();
		try {
			DataModel model = MovieRatingsDataFileModel.instance();
			UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
			similarity.setPreferenceInferrer(new AveragingPreferenceInferrer(model));
			UserNeighborhood neighborhood = new NearestNUserNeighborhood(DataMiningUserCFGoal.NEAREST_NEIGHBOUR, similarity, model);
			Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
			CachingRecommender cachingRecommender = new CachingRecommender(recommender);
			List<RecommendedItem> result = cachingRecommender.recommend(Integer.parseInt(userid), DataMiningUserCFGoal.HOMW_MANY);
			// If don't create GenericRecommendedItem, the return result will have zero ID, and value, I dont know why
			for (Iterator<RecommendedItem> iterator = result.iterator(); iterator.hasNext(); ) {
				RecommendedItem item = iterator.next();
				miningRes.add(new GenericRecommendedItem(item.getItemID(), item.getValue()));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("User CF: mineRecommendations end " + miningRes);
		
		return miningRes;		
	}
	
	
}
