package views;

import controller.EmergencySystem;
import java.util.List;
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

        initializeResources(emergencySystem);

        Scanner sc = new Scanner(System.in);
        boolean menu = true;


        while (menu) {
            System.out.println("\n ----   SISTEMA DE GESTION DE EMERGENCIAS   ----");
            System.out.println("1. Registrar emergencia");
            System.out.println("2. Mostrar recursos");
            System.out.println("3. Atender emergencia");
            System.out.println("4. Ver estadisticas del dia");
            System.out.println("5. Finalizar jornada");
            System.out.println("Selecciona una opcion: ");
            int option = Integer.parseInt(sc.nextLine());

            switch (option) {

                case 1 :
                    registerEmergencyFromMenu(emergencySystem, sc);
                    break;
                case 2 :
                    emergencySystem.showResourcesStatus();
                    break;
                case 3 :
                    attendEmergencyFromMenu(emergencySystem, sc);
                    break;
                case 4 :
                    emergencySystem.showStatistics();
                    break;
                case 5 : 
                    emergencySystem.endDay();
                    menu = false;
                    break;
                default:
                    System.out.println("Opcion no valida.");              
            }
        }

        sc.close();

    }


    private static void initializeResources(EmergencySystem system){

        system.registerResource(new Firefighters("Bomberos 1", 8, 200));
        system.registerResource(new Firefighters("Bomberos 2", 4, 170));
        system.registerResource(new Ambulance("Ambulancia 1", 4, 190));
        system.registerResource(new Ambulance("Ambulancia 2", 3, 200));
        system.registerResource(new Police("Policia 1", 4, 250));
        system.registerResource(new Police("Policia 2", 2, 170));

    }

    private static void registerEmergencyFromMenu(EmergencySystem system, Scanner sc){

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
        /* System.out.println("Zona-Norte,Zona-Sur, Zona-Centro, Zona-Oriente, Zona-Occidente"); */
        System.out.println("1. Zona-Norte");
        System.out.println("2. Zona-Sur");
        System.out.println("3. Zona-Centro");
        System.out.println("4. Zona-Oriente");        
        System.out.println("5. Zona-Occidente");

        String ubication = null;

        switch(Integer.parseInt(sc.nextLine())){

            case 1 -> ubication = "Zona-Norte";
            case 2 -> ubication = "Zona-Sur";
            case 3 -> ubication = "Zona-Centro";
            case 4 -> ubication = "Zona-Oriente";
            case 5 -> ubication = "Zona-Occidente";
            default -> ubication = "Zona-Norte";
        }

        

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

    private static void attendEmergencyFromMenu(EmergencySystem system, Scanner sc){

        List<Emergency> pending = system.getEmergencies();

        if(pending.isEmpty()){
            System.out.println("No hay emergencias pendientes.");
            return;
        }

        System.out.println("\n--- ATENDER UNA EMERGENCIA ---");

        for(int i = 0; i < pending.size(); i++){
            System.out.println((i+1) + ". " + pending.get(i).getDescription());
        }

        System.out.println("Selecciona la emergencia a atender: ");
        int option = Integer.parseInt(sc.nextLine()) - 1;
        if(option < 0 || option >= pending.size()){
            System.out.println("Opcion no valida.");
            return;
        }

        Emergency emergency = pending.get(option);

        system.assignResourcesToEmergency(emergency);
        system.attendEmergency(emergency);


    }



}
