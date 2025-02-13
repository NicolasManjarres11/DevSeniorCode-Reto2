package model.services;

import model.Emergency;

public class Ambulance extends EmergencyService{

    public Ambulance(String id, int staff, int fuel) {
            super(id, staff, fuel);
        }
    
        @Override
    public void attendEmergency(Emergency emergency) {
        
        System.out.println("Ambulancia en camino");
        System.out.println("[Ambulancia "+getId()+" ]" + emergency.getDescription());

        assignStaff(1);
        assignFuel(30);
    }

}
