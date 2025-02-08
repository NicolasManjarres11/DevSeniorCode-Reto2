package model.factory;

import model.Accident;
import model.Emergency;
import model.Fire;
import model.Robbery;
import model.services.EmergencyService;
import utils.TypeEmergency;

public class FactoryEmergency {

    public static Emergency createEmergency (TypeEmergency type, String location, int gravity, int responseTime){

        switch (type) {
            case FIRE:
            return new Fire(location, gravity, responseTime);

            case ACCIDENT:
            return new Accident(location, gravity, responseTime);

            case ROBBERY:
            return new Robbery( location, gravity, responseTime);
        
            default:
                return null;
        }
    }


}
