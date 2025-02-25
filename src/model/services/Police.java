package model.services;

import model.Emergency;

public class Police extends EmergencyService{

    public Police(String id, int staff, int fuel) {
            super(id, staff, fuel);
            //TODO Auto-generated constructor stub
        }
    
        @Override
    public void attendEmergency(Emergency emergency) {
        System.out.println("Policias en camino");
        System.out.println("[Policias "+getId()+" ]" +" "+ emergency.getDescription());

        assignStaff(1);
        assignFuel(30);

        System.out.println("Policias restantes: "+ getStaff() + " | Combustible restante: "+ getFuel());
    }

}
