package model;

import utils.Gravity;

public class Fire extends Emergency {

    public Fire(String location, Gravity gravity, int responseTime) {
        super("Incendio", location, gravity, responseTime);
    }

}
