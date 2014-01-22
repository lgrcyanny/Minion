package com.mas.recomm.agent.search;

import com.mas.recomm.agent.common.SearchGoal;

import jadex.bdiv3.annotation.Goal;
import jadex.bdiv3.annotation.Goals;
import jadex.bdiv3.annotation.Publish;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.Description;

@Agent
@Description("SearchBDI Agent, provide searching service for details about recommended items.")
@Goals(@Goal(clazz=SearchGoal.class, publish=@Publish(type=ISearchService.class)))
public class SearchBDI {

}
