package model.interfaces;

import model.Emergency;

public interface IEmergencyService {

    String getId();

    int getStaff();

    int getFuel();

    boolean isStatus();

    void assignStaff(int amount);

    void releaseStaff(int amount);

    void assignFuel(int amount);

    void releaseFuel(int amount);

    void attendEmergency(Emergency emergency);

    void finishEmergency(Emergency emergency);



}
