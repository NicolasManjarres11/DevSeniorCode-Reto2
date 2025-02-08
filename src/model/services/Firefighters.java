package model.services;

import model.Emergency;

public class Firefighters extends EmergencyService{

    public Firefighters(String id, int staff, int fuel) {
            super(id, staff, fuel);
            //TODO Auto-generated constructor stub
        }
    
        @Override
    public void attendEmergency(Emergency emergency) {
        System.out.println("Ambulancia en camino");
        System.out.println("[Ambulancia "+getId()+" ]" + emergency.getDescription());

        assignStaff(1);
        assignFuel(30);
    }

}
