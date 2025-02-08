package model.interfaces;

public interface IEmergencyService {

    String getId();

    int getStaff();

    int getFuel();

    boolean isStatus();

    void assignStaff(int amount);

    void releaseStaff(int amount);

    void assignFuel(int amount);

    void releaseFuel(int amount);



}
