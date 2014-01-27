package com.mas.recomm.agent.strategy;

import jadex.commons.future.IFuture;

public interface IStrategyService {
	
	public IFuture<String> makeAlgorithmStrategy(String userid);

}
