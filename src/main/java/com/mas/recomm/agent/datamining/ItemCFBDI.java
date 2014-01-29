package com.mas.recomm.agent.datamining;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericRecommendedItem;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

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
@Description("Mining Recommendations with Item CF")
@Service
@Goals(@Goal(clazz=DataMiningItemCFGoal.class, publish=@Publish(type=IItemCFService.class)))
public class ItemCFBDI {
	@Agent
	BDIAgent agent;
	
	@Plan(trigger=@Trigger(goals=DataMiningItemCFGoal.class))
	public List<RecommendedItem> mineRecommendations(String[] params) {
		String userid = params[0];
		String strategy = params[1];
		System.out.println("Item CF : mineRecommendations start, strategy is " + strategy + ", userid is " + userid);
		List<RecommendedItem> miningRes = new ArrayList<RecommendedItem>();
		try {
			DataModel model = MovieRatingsDataFileModel.instance();
			ItemSimilarity similarity = new PearsonCorrelationSimilarity(model);
			Recommender recommender = new GenericItemBasedRecommender(model, similarity);
			CachingRecommender cachingRecommender = new CachingRecommender(recommender);
			List<RecommendedItem> result = cachingRecommender.recommend(Integer.parseInt(userid), DataMiningItemCFGoal.HOMW_MANY);
			// If don't create GenericRecommendedItem, the return result will have zero ID, and value, I dont know why
			for (Iterator<RecommendedItem> iterator = result.iterator(); iterator.hasNext(); ) {
				RecommendedItem item = iterator.next();
				miningRes.add(new GenericRecommendedItem(item.getItemID(), item.getValue()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Item CF: mineRecommendations end " + miningRes);
		
		return miningRes;		
	}

}
