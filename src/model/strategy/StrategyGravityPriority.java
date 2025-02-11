package model.strategy;

import model.Emergency;

public class StrategyGravityPriority implements  StrategyPriority{

    @Override
    public int calculatePriority(Emergency emergency) {
        return switch (emergency.getGravity()) {
            case HIGH -> 3;
            case MEDIUM -> 2;
            case LOW -> 1;
            default -> 1;
        };
    }

}
