package com.mas.recomm.agent.recommbuilder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import com.mas.recomm.model.RecommendedMovieItem;

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
@Description("RecommendationBuilerBDI Agent, build recommended items for display.")
@Service
@Goals(@Goal(clazz=RecommendationBuildGoal.class, publish=@Publish(type=IRecommendationBuildService.class)))
public class RecommendationBuilderBDI{
	@Agent
	protected BDIAgent agent;
	
	@AgentCreated
	public void init() {
		
	}

	@Plan(trigger=@Trigger(goals=RecommendationBuildGoal.class))
	public List<RecommendedMovieItem> buildRecommendationRes(List[] params) {
		List<RecommendedItem> miningRes = params[0];
		List<ResultSet> searchRes = params[1];
		System.out.println("buildRecommendationRes start " + miningRes);
		List<RecommendedMovieItem> ret = new ArrayList<RecommendedMovieItem>();
		Iterator<RecommendedItem> iterator1 = miningRes.iterator();
		Iterator<ResultSet> iterator2 = searchRes.iterator();
		while(iterator1.hasNext()) {
			RecommendedItem miningItem = iterator1.next();
			ResultSet rsItem = iterator2.next();
			try {
				rsItem.first();
				RecommendedMovieItem movieItem = new RecommendedMovieItem(
						(int) miningItem.getItemID(),
						rsItem.getString("title"),
						rsItem.getString("genres"),
						miningItem.getValue());
				ret.add(movieItem);
				rsItem.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("buildRecommendationRes end");
		return ret;
	}
}
