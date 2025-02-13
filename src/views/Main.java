package views;

import controller.EmergencySystem;
import java.util.Scanner;
import model.Emergency;
import model.factory.FactoryEmergency;
import model.services.Ambulance;
import model.services.Firefighters;
import model.services.Police;
import utils.Gravity;
import utils.TypeEmergency;

public class Main {
    public static void main(String[] args) throws Exception {
        
        EmergencySystem emergencySystem = EmergencySystem.getInstance();
        boolean menu = true;




    }


    public void initializeResources(EmergencySystem system){

        system.registerResource(new Firefighters("Bomberos 1", 8, 200));
        system.registerResource(new Firefighters("Bomberos 2", 4, 170));
        system.registerResource(new Ambulance("Ambulancia 1", 4, 190));
        system.registerResource(new Ambulance("Ambulancia 2", 3, 200));
        system.registerResource(new Police("Policia 1", 4, 250));
        system.registerResource(new Police("Policia 2", 2, 170));

    }

    public void registerEmergencyFromMenu(EmergencySystem system, Scanner sc){

        System.out.println("\n--- REGISTRAR EMERGENCIA ---");
        System.out.println("1. Accidente");
        System.out.println("2. Robo");
        System.out.println("3. Incendio");
        System.out.print("Selecciona el tipo de emergencia: ");
        TypeEmergency type = null;

        switch(Integer.parseInt(sc.nextLine())){

            case 1 -> type = TypeEmergency.ACCIDENT;
            case 2 -> type = TypeEmergency.ROBBERY;
            case 3 -> type = TypeEmergency.FIRE;
            default -> System.out.println("Opcion no valida.");

        }

        System.out.println("Ingresa la ubicación de estas siguientes opciones ");
        System.out.println("Zona-Norte,Zona-Sur, Zona-Centro, Zona-Oriente, Zona-Occidente");
        String ubication = sc.nextLine();

        System.out.println("Ingresa la gravedad de estas siguientes opciones ");
        System.out.println("1. Baja");
        System.out.println("2. Media");
        System.out.println("3. Alta");

        Gravity gravity = null;

        switch(Integer.parseInt(sc.nextLine())){

            case 1 -> gravity = Gravity.LOW;
            case 2 -> gravity = Gravity.MEDIUM;
            case 3 -> gravity = Gravity.HIGH;
            default -> gravity = Gravity.LOW;

        }

        System.out.println("Ingresa el tiempo estimado de atención en minutos");

        int responseTime = Integer.parseInt(sc.nextLine());

        Emergency emergency = FactoryEmergency.createEmergency(type, ubication, gravity, responseTime);

        if(emergency == null){
            System.out.println("Error al crear la emergencia, intenta de nuevo.");
            return;
        }

        system.addEmergency(emergency);
        System.out.println("La emergencia ha sido registrada con exito: " + emergency);

    }


    
}
