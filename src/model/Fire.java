package model;

import utils.Gravity;
import utils.TypeEmergency;

public class Fire extends Emergency {

    public Fire(String type, String location, int gravity, int responseTime) {
        super("Incendio", location, gravity, responseTime);
    }

}
