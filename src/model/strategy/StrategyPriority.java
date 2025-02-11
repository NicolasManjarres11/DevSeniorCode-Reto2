package model.strategy;

import model.Emergency;

public interface  StrategyPriority {

    int calculatePriority(Emergency emergency);

}
