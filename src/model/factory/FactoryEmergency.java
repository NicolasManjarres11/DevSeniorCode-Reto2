package model.factory;

import model.Accident;
import model.Emergency;
import model.Fire;
import model.Robbery;
import utils.Gravity;
import utils.TypeEmergency;

public class FactoryEmergency {

    public static Emergency createEmergency (TypeEmergency type, String location, Gravity gravity, int responseTime){

        return switch (type) {
            case FIRE -> new Fire(location, gravity, responseTime);
            case ACCIDENT -> new Accident(location, gravity, responseTime);
            case ROBBERY -> new Robbery( location, gravity, responseTime);
            default -> null;
        };
    }


}
