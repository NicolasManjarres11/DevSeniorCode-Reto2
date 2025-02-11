package model.strategy;

import model.Emergency;

public class StrategyProximityPriority implements StrategyPriority{


    private class UrbanMap {

        public int calculateDistance(String ubication){

            switch (ubication.toLowerCase()) {
                case "zona-norte":
                    return 8;
                case "zona-sur":
                    return 10;
                case "zona-centro":
                    return 2;
                case "zona-oriente":
                    return 5;
                case "zona-occiendente":
                    return 6;
                default:
                    return 10;
            }

        }


    }

    private UrbanMap urbanMap = new UrbanMap();



    @Override
    public int calculatePriority(Emergency emergency) {
        int calculateDistance = urbanMap.calculateDistance(emergency.getLocation());
        return 10 -calculateDistance;
    }

}
