package com.mas.recomm.agent.strategy;

import java.sql.Connection;
import java.sql.*;

import com.mas.recomm.model.DataSourceFactory;

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
@Description("StrategyBDI Agent, make algorithm strategy decision on Item CF or User CF")
@Service
@Goals(@Goal(clazz=StrategyGoal.class, publish=@Publish(type=IStrategyService.class)))
public class StrategyBDI {
	@Agent
	BDIAgent agent;
	
	
	/**
	 * the  standard deviation of all ratings by an user is  the self item similarity
	 * if the standard < 0.8, we think user prefer similar movies, adopt Item CF
	 * default is User CF
	 * standard is calculated by SQL
	 * @param userid
	 * @return String The Strategy
	 */
	@Plan(trigger=@Trigger(goals=StrategyGoal.class))
	public String makeAlgorithmStrategy (String userid) {
		System.out.println("makeAlgorithmStrategy start");
		String ret = StrategyGoal.STRATEGY_CF_USER;
		String sql = "select sqrt(VARIANCE(rating)) as standard from movielens.tbl_ratings where userid = ?  group by userid";
		try {
			Connection connection = DataSourceFactory.getMySQLDataSource().getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, userid);
			ResultSet rs = statement.executeQuery();
			double standard = 0;
			while(rs.next()) {
				standard = rs.getDouble("standard");
			}
			if (standard < StrategyGoal.STRATEGY_EXPECT_STANDARD) {
				ret = StrategyGoal.STRATEGY_CF_ITEM;
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("makeAlgorithmStrategy end " + ret);
		return ret;
	}

}
