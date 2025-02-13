package model.services;

import model.Emergency;
import model.interfaces.IEmergencyService;

public abstract class EmergencyService implements IEmergencyService {

    private String id;
    private int staff;
    private int fuel;

    
    

    public EmergencyService(String id, int staff, int fuel) {
        this.id = id;
        this.staff = staff;
        this.fuel = fuel;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public int getStaff() {
        return staff;
    }

    @Override
    public int getFuel() {
        return fuel;
    }

    @Override
    public boolean isStatus() {
        return staff > 0 && fuel > 0;
    }

    @Override
    public void assignStaff(int amount) {
        if(amount < staff){
            staff -= amount;
        } else{
            System.out.println("Pesonal insuficiente: "+id);
        }
    }

    @Override
    public void releaseStaff(int amount) {
        staff += amount;
    }

    @Override
    public void assignFuel(int amount) {
        fuel = Math.max(0, fuel - amount);
    }

    @Override
    public void releaseFuel(int amount) {
        fuel += amount;
    }

    

    @Override
    public abstract void attendEmergency(Emergency emergency);

    @Override
    public String toString() {
        return "Servicio de emergencia [Id: " + id + ", Personal disponible: " + staff + ", Combustible: " + fuel + "]";
    }

    

    

}
