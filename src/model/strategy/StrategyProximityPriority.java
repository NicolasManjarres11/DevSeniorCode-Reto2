package model.strategy;

import model.Emergency;

public class StrategyProximityPriority implements StrategyPriority{


    private class UrbanMap {

        public int calculateDistance(String ubication){

            return switch (ubication.toLowerCase()) {
                case "zona-norte" -> 8;
                case "zona-sur" -> 10;
                case "zona-centro" -> 2;
                case "zona-oriente" -> 5;
                case "zona-occidente" -> 6;
                default -> 10;
            };

        }


    }

    private UrbanMap urbanMap = new UrbanMap();



    @Override
    public int calculatePriority(Emergency emergency) {
        int calculateDistance = urbanMap.calculateDistance(emergency.getLocation());
        return 10 -calculateDistance;
    }

}
