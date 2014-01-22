package com.mas.recomm.agent.datamining;

import com.mas.recomm.agent.common.DataMiningGoal;

import jadex.bdiv3.annotation.Goal;
import jadex.bdiv3.annotation.Goals;
import jadex.bdiv3.annotation.Publish;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.Description;

@Agent
@Description("DataMiningBDI Agent, provide mining recommendations service.")
@Goals(@Goal(clazz=DataMiningGoal.class, publish=@Publish(type=IDataMiningService.class)))
public class DataMiningBDI {

}
