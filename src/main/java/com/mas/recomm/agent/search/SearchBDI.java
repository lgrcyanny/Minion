package com.mas.recomm.agent.search;

import java.sql.*;
import java.util.*;

import org.apache.mahout.cf.taste.impl.recommender.GenericRecommendedItem;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import com.mas.recomm.agent.common.SearchGoal;
import com.mas.recomm.model.DataSourceFactory;

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
@Description("SearchBDI Agent, provide searching service for details about recommended items.")
@Service
@Goals(@Goal(clazz=SearchGoal.class, publish=@Publish(type=ISearchService.class)))
public class SearchBDI {
	@Agent
	BDIAgent agent;
	
	@AgentCreated
	public void init() {
		
	}

	@Plan(trigger=@Trigger(goals=SearchGoal.class))
	public List<ResultSet> searchRecommendedDetails(List<RecommendedItem> miningRes) {
		System.out.println("searchDetails start ");
//		System.out.println("search for " + miningRes);
		List<ResultSet> ret = new ArrayList<ResultSet>();
		try {
			Connection connection = DataSourceFactory.getMySQLDataSource().getConnection();
			for(Iterator<RecommendedItem> iterator = miningRes.iterator(); iterator.hasNext(); ) {
				RecommendedItem item = iterator.next();
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM movielens.tbl_movies WHERE movieid=?");
				statement.setString(1, String.valueOf(item.getItemID()));
				ResultSet rs = statement.executeQuery();
				ret.add(rs);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("searchDetails end");
		return ret;
	}

}
