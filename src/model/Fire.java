package model;

public class Fire extends Emergency {

    public Fire(String location, int gravity, int responseTime) {
        super("Incendio", location, gravity, responseTime);
    }

}
