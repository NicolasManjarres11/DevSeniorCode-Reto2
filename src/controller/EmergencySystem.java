package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import model.Accident;
import model.Emergency;
import model.Fire;
import model.Robbery;
import model.interfaces.IEmergencyService;
import model.observer.ObserverEmergency;
import model.observer.SubjectEmergency;
import model.services.Ambulance;
import model.services.Firefighters;
import model.services.Police;
import model.strategy.StrategyGravityPriority;
import model.strategy.StrategyPriority;

public class EmergencySystem implements SubjectEmergency {

    private static EmergencySystem instance;
    private List<Emergency> listEmergency;
    private List<IEmergencyService> listResources;
    private List<ObserverEmergency> observers;

    private StrategyPriority strategyPriority;

    private int emergenciesAttend;
    private long totalAttentionTime;

    private Database database = new Database();

    private EmergencySystem() {

        strategyPriority = new StrategyGravityPriority();
        listEmergency = new ArrayList<>();
        listResources = new ArrayList<>();
        observers = new ArrayList<>();
        emergenciesAttend = 0;
        totalAttentionTime = 0;

    }

    

    public List<IEmergencyService> getListResources() {
        return listResources;
    }



    //Se instancia una vez el sistema de emergencias //Singleton
    public static EmergencySystem getInstance() {
        if (instance == null) {
            instance = new EmergencySystem();
        }

        return instance;
    }

    public void loadEmergenciesFromFile() throws IOException {
        listEmergency = database.loadEmergencies();
    }

    @Override
    public void addObserver(ObserverEmergency observerEmergency) {
        observers.add(observerEmergency);
    }

    @Override
    public void removeObserver(ObserverEmergency observerEmergency) {
        observers.remove(observerEmergency);
    }

    @Override
    public void notifyObservers(Emergency emergency) {
        for (ObserverEmergency observerEmergency : observers) {
            observerEmergency.onNewEmergency(emergency);
        }
    }

    public void registerResource(IEmergencyService resource) {
        listResources.add(resource);
    }

    public void showResourcesStatus() {
        System.out.println("\n--- ESTADO ACTUAL DE LOS RECURSOS ---");
        for (IEmergencyService resource : listResources) {
            System.out.println(resource.toString());
        }
    }

    

    //Lambda
    public List<IEmergencyService> filterAvailableResources() {
        return listResources.stream().filter(r -> r.isStatus()).collect(Collectors.toList());
    }

    public void addEmergency(Emergency emergency) {
        listEmergency.add(emergency);
        database.saveEmergencies(listEmergency);
        notifyObservers(emergency);
    }

    public List<Emergency> getEmergencies() {

        return listEmergency.stream().filter(e -> !e.isStatus()).collect(Collectors.toList());
    }

    //Obtenet emergencias segun prioridad

    public List<Emergency> getPendingEmergenciesSorted() {
        return listEmergency.stream()
                .filter(e -> !e.isStatus())
                .sorted((e1, e2) -> Integer.compare(
                    strategyPriority.calculatePriority(e2),  // Emergencia con mayor prioridad primero
                    strategyPriority.calculatePriority(e1)))
                .collect(Collectors.toList());
    }

    public IEmergencyService assignResourcesToEmergency(Emergency emergency) {
        List<IEmergencyService> available = filterAvailableResources();
        if (available.isEmpty()) {
            System.out.println("No hay recursos disponibles para atender la emergencia");
            return null;
        }
        System.out.println("\n--- ASIGNANDO RECURSOS ... ---");
    
        for (IEmergencyService r : available) {
            if ((emergency instanceof Fire && r instanceof Firefighters) ||
                (emergency instanceof Accident && r instanceof Ambulance) ||
                (emergency instanceof Robbery && r instanceof Police)) {
                
                r.attendEmergency(emergency);
                return r;
            }
        }
        return null;
    }

    public void attendEmergency(Emergency emergency) {

        if (emergency.isStatus()) {
            System.out.println("Esta emergencia ya ha sido atendida");
            return;
        }

        emergency.startAttention(); //Inicia el contador en lo que se demora atender una emergencia

        IEmergencyService assignedResource = assignResourcesToEmergency(emergency);

        try {

            for (int i = 0; i <= 100; i++) {

                switch (i) {
                    case 0 ->
                        System.out.println("Iniciando atencion de emergencia: " + emergency.getDescription());
                    case 50 ->
                        System.out.println("50% de atención de emergencia");
                    case 100 ->
                        System.out.println("Emergencia atendida");
                    default -> {
                    }
                }

                System.out.println("La emergencia se ha atendido en un " + i + "%");
                System.out.println();

                Thread.sleep(emergency.getResponseTime() * 10);

            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        emergency.endAttention(); //Finaliza el contador a lo que se termina de gestionar una emergencia

        System.out.println("Emergencia atendida: " + emergency.getDescription());

        emergency.setStatus(true);

        emergenciesAttend += 1;
        totalAttentionTime += emergency.getResponseTime();
        database.saveEmergencies(getEmergencies());

        //Se dejan disponibles de nuevo las personas en staff cuando finalizan la atencion de una emergencia	

        if (assignedResource != null) {
            
            assignedResource.finishEmergency(emergency);
        }

    }

    public void showStatistics() {

        System.out.println("\n--- ESTADISTICAS DEL DIA ---");
        System.out.println("Emergencias atendidas: " + emergenciesAttend);

        long mediaMs = 0;

        if (emergenciesAttend > 0) {
            mediaMs = totalAttentionTime / emergenciesAttend;
        }

        double mediaSeg = mediaMs / 1000.0;

        System.out.println("Tiempo promedio de atencion: " + mediaSeg + " segundos");

        long noAttend = listEmergency.stream().filter(e -> !e.isStatus()).count();

        System.out.println("Emergencias no atendidas: " + noAttend);
    }

    public void endDay() {
        showStatistics();
        try {
            System.out.println("\nGuardando registro del día ...");
            database.saveEmergencies(getEmergencies());
            Thread.sleep(3000);

        } catch (InterruptedException e) {
        }

        System.out.println("Sistema preparado para siguiente ciclo.");

    }

    public void setStrategyPriority(StrategyPriority newStrategy) {
        this.strategyPriority = newStrategy;
    }

    public List<Emergency> getListEmergency() {
        return listEmergency;
    }

}
