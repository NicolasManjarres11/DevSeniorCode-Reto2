package views;

import controller.Database;
import controller.EmergencySystem;
import java.util.List;
import java.util.Scanner;
import model.Emergency;
import model.factory.FactoryEmergency;
import model.interfaces.IEmergencyService;
import model.services.Ambulance;
import model.services.Firefighters;
import model.services.Police;
import utils.Gravity;
import utils.TypeEmergency;

public class Main {

    public static void main(String[] args) throws Exception {

        Database database = new Database();

        EmergencySystem emergencySystem = EmergencySystem.getInstance();
        emergencySystem.loadEmergenciesFromFile();

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

                case 1:
                    registerEmergencyFromMenu(emergencySystem, sc);
                    break;
                case 2:
                    emergencySystem.showResourcesStatus();
                    System.out.println("\n¿Deseas transferir recursos?");
                    System.out.println("1. Si");
                    System.out.println("2. No");
                    int optionTransfer = Integer.parseInt(sc.nextLine());
                    if (optionTransfer == 1) {
                        transferResources(emergencySystem, sc);
                    }
                    break;
                case 3:
                    attendEmergencyFromMenu(emergencySystem, sc);
                    break;
                case 4:
                    emergencySystem.showStatistics();
                    break;
                case 5:
                    emergencySystem.endDay();
                    menu = false;
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        }

        sc.close();

    }

    private static void initializeResources(EmergencySystem system) {

        system.registerResource(new Firefighters("Bomberos 1", 8, 200));
        system.registerResource(new Firefighters("Bomberos 2", 4, 170));
        system.registerResource(new Ambulance("Ambulancia 1", 4, 190));
        system.registerResource(new Ambulance("Ambulancia 2", 3, 200));
        system.registerResource(new Police("Policia 1", 4, 250));
        system.registerResource(new Police("Policia 2", 2, 170));

    }

    //Metodo para transferir recursos de un servicio a otro
    private static void transferResources(EmergencySystem emergencySystem, Scanner sc) {

        List<IEmergencyService> listResources = emergencySystem.getListResources();

        if (listResources.isEmpty()) {
            System.out.println("No hay recursos disponibles para transferir.");
            return;
        }

        System.out.println("\n--- TRANSFERIR RECURSOS ---");

        for (int i = 0; i < listResources.size(); i++) {
            System.out.println((i + 1) + ". " + listResources.get(i).toString());
        }

        System.out.println("\nSelecciona el recurso origen: ");
        int origin = Integer.parseInt(sc.nextLine()) - 1;

        if (origin < 0 || origin >= listResources.size()) {

            System.out.println("Opcion no valida.");
            return;
        }

        System.out.println("\nSelecciona el recurso destino: ");
        int destination = Integer.parseInt(sc.nextLine()) - 1;

        if (destination < 0 || destination >= listResources.size()) {

            System.out.println("Opcion no valida.");
            return;
        }

        System.out.println("\n¿Que recurso deseas transferir?");
        System.out.println("1. Personal");
        System.out.println("2. Combustible");
        int option = Integer.parseInt(sc.nextLine());

        if (option != 1 && option != 2) {
            System.out.println("Opcion no valida.");
            return;
        } else if (option == 1) {

            System.out.println("\nIngresa la cantidad de personal a transferir: ");
            int personal = Integer.parseInt(sc.nextLine());

            if (personal <= 0) {
                System.out.println("La cantidad de personal a transferir debe ser mayor a 0.");
                return;
            }

            IEmergencyService originResource = listResources.get(origin);
            IEmergencyService destinationResource = listResources.get(destination);

            if (originResource.getStaff() < personal) {
                System.out.println("No hay suficiente personal para transferir.");
                return;
            }

            originResource.assignStaff(personal);
            destinationResource.releaseStaff(personal);

            System.out.println("\nPersonal transferido.");
            System.out.println("Recurso origen: " + originResource);
            System.out.println("Recurso destino: " + destinationResource);

        } else if (option == 2) {
            System.out.println("\nIngresa la cantidad de combustible a transferir: ");
            int fuel = Integer.parseInt(sc.nextLine());

            if (fuel <= 0) {
                System.out.println("La cantidad de personal a transferir debe ser mayor a 0.");
                return;
            }

            IEmergencyService originResource = listResources.get(origin);
            IEmergencyService destinationResource = listResources.get(destination);

            if (originResource.getFuel() < fuel) {
                System.out.println("No hay suficiente combustible para transferir.");
                return;
            }

            originResource.assignFuel(fuel);
            destinationResource.releaseFuel(fuel);

            System.out.println("\nCombustible transferido.");
            System.out.println("Recurso origen: " + originResource);
            System.out.println("Recurso destino: " + destinationResource);
        }

    }

    private static void registerEmergencyFromMenu(EmergencySystem system, Scanner sc) {

        /* var menu = true; */
        System.out.println("\n--- REGISTRAR EMERGENCIA ---");
        System.out.println();

        TypeEmergency type = null;
        int typeOption;

        do {

            System.out.println("Selecciona el tipo de emergencia a reportar: ");
            System.out.println("\n1. Accidente");
            System.out.println("2. Robo");
            System.out.println("3. Incendio");
            System.out.print("\nIngresa una opcion: ");
            typeOption = Integer.parseInt(sc.nextLine());

            switch (typeOption) {

                case 1 ->
                    type = TypeEmergency.ACCIDENT;
                case 2 ->
                    type = TypeEmergency.ROBBERY;
                case 3 ->
                    type = TypeEmergency.FIRE;
                default -> {
                    System.err.println("Opcion no valida. Intentelo de nuevo.");
                }

            }

        } while (typeOption != 1 && typeOption != 2 && typeOption != 3);

        String ubication = null;
        int ubicationOption;

        System.out.println("\nIngresa la ubicación donde se presento la emergencia: ");

        do {

            System.out.println("\n1. Zona-Norte");
            System.out.println("2. Zona-Sur");
            System.out.println("3. Zona-Centro");
            System.out.println("4. Zona-Oriente");
            System.out.println("5. Zona-Occidente");
            System.out.print("\nIngresa una opcion: ");
            ubicationOption = Integer.parseInt(sc.nextLine());

            switch (ubicationOption) {

                case 1 ->
                    ubication = "Zona-Norte";
                case 2 ->
                    ubication = "Zona-Sur";
                case 3 ->
                    ubication = "Zona-Centro";
                case 4 ->
                    ubication = "Zona-Oriente";
                case 5 ->
                    ubication = "Zona-Occidente";
                default ->
                    System.err.println("Opcion no valida. Intentelo de nuevo.");
            }

        } while (!(ubicationOption >= 1 && ubicationOption <= 5));

        Gravity gravity = null;
        int gravityOption;

        do {

            System.out.println("\nIngresa la gravedad de estas siguientes opciones ");
            System.out.println("\n1. Baja");
            System.out.println("2. Media");
            System.out.println("3. Alta");
            System.out.print("\nIngresa una opcion: ");
            gravityOption = Integer.parseInt(sc.nextLine());

            switch (gravityOption) {

                case 1 ->
                    gravity = Gravity.LOW;
                case 2 ->
                    gravity = Gravity.MEDIUM;
                case 3 ->
                    gravity = Gravity.HIGH;
                default ->
                    System.err.println("Opcion no valida. Intentelo de nuevo.");

            }

        } while (!(gravityOption >= 1 && gravityOption <= 3));

        int responseTime;

        do {
            System.out.print("\nIngresa el tiempo estimado de atención en minutos: ");

            responseTime = Integer.parseInt(sc.nextLine());
            if (responseTime <= 0) {
                System.err.println("El tiempo estimado no puede ser negativo.");
            }

        } while (responseTime <= 0);

        Emergency emergency = FactoryEmergency.createEmergency(type, ubication, gravity, responseTime);

        if (emergency == null) {
            System.out.println("Error al crear la emergencia, intenta de nuevo.");
            return;
        }

        System.out.println("¿Desea registrar la siguiente emergencia?");
        System.out.println(emergency);
        System.out.println("\n1. Si");
        System.out.println("2. No");
        System.out.println("Ingresa una opcion: ");
        int option = Integer.parseInt(sc.nextLine());

        if (option != 1) {
            System.out.println("La emergencia no ha sido registrada.");
            return;
        }

        system.addEmergency(emergency);
        System.out.println("\nLa emergencia ha sido registrada con exito: " + emergency);

    }

    private static void attendEmergencyFromMenu(EmergencySystem system, Scanner sc) {

        List<Emergency> pending = system.getEmergencies();
        List<Emergency> pendingByPriority = system.getPendingEmergenciesSorted();

        if (pending.isEmpty() || pendingByPriority.isEmpty()) {
            System.out.println("No hay emergencias pendientes.");
            return;
        }

        System.out.println("\n--- ATENDER UNA EMERGENCIA ---");
        System.out.println();

        System.out.println("¿Como deseas atender las emergencias?");
        System.out.println("1. Por prioridad");
        System.out.println("2. Por orden de llegada");
        System.out.println("Ingresa una opcion: ");
        int optionAttend = Integer.parseInt(sc.nextLine());

        if (optionAttend <= 0 || optionAttend > 2) {
            System.out.println("Opcion no valida.");
            return;
        }

        if (optionAttend == 1) {
            System.out.println("\n--- Emergencias pendientes (ordenadas por prioridad) ---");
            for (int i = 0; i < pendingByPriority.size(); i++) {
                Emergency e = pendingByPriority.get(i);
                System.out.println((i + 1) + ". " + e.getDescription()
                        + " | Prioridad: " + system.getStrategyPriority().calculatePriority(e));
            }

            System.out.println("\nSelecciona la emergencia a atender: ");
            int option = Integer.parseInt(sc.nextLine()) - 1;
            if (option < 0 || option >= pendingByPriority.size()) {
                System.out.println("Opcion no valida.");
                return;
            }

            Emergency emergency = pendingByPriority.get(option);

            system.assignResourcesToEmergency(emergency);
            system.attendEmergency(emergency);

            system.showResourcesStatus();

        } if (optionAttend == 2) {

        for (int i = 0; i < pending.size(); i++) {
            System.out.println((i + 1) + ". " + pending.get(i).getDescription());
        }

        System.out.println("\nSelecciona la emergencia a atender: ");
        int option = Integer.parseInt(sc.nextLine()) - 1;
        if (option < 0 || option >= pending.size()) {
            System.out.println("Opcion no valida.");
            return;
        }

        Emergency emergency = pending.get(option);

        system.assignResourcesToEmergency(emergency);
        system.attendEmergency(emergency);

        system.showResourcesStatus();

    }

    }

}
