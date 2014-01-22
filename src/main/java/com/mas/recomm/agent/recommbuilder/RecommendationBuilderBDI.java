package com.mas.recomm.agent.recommbuilder;

import com.mas.recomm.agent.common.RecommendationBuildGoal;

import jadex.bdiv3.annotation.Goal;
import jadex.bdiv3.annotation.Goals;
import jadex.bdiv3.annotation.Publish;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.Description;

@Agent
@Description("RecommendationBuilerBDI Agent, build recommended items for display.")
@Goals(@Goal(clazz=RecommendationBuildGoal.class, publish=@Publish(type=IRecommendationBuildService.class)))
public class RecommendationBuilderBDI {

}
