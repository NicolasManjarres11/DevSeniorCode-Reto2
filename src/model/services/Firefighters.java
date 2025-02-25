package model.services;

import model.Emergency;

public class Firefighters extends EmergencyService{

    public Firefighters(String id, int staff, int fuel) {
            super(id, staff, fuel);
            //TODO Auto-generated constructor stub
        }
    
        @Override
    public void attendEmergency(Emergency emergency) {
        System.out.println("Bomberos en camino");
        System.out.println("[Bomberos "+getId()+" ]" +" "+ emergency.getDescription());

        assignStaff(2);
        assignFuel(30);

        System.out.println("Bomberos restantes: "+ getStaff() + " | Combustible restante: "+ getFuel());
    }

}
