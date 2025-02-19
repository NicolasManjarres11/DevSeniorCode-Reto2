package controller;

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

    //Se instancia una vez el sistema de emergencias //Singleton
    public static EmergencySystem getInstance() {
        if (instance == null) {
            instance = new EmergencySystem();
        }

        return instance;
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

    public void assignResourcesToEmergency(Emergency emergency) {

        List<IEmergencyService> available = filterAvailableResources();

        if (available.isEmpty()) {
            System.out.println("No hay recursos disponibles para atender la emergencia");
            return;
        }
        System.out.println("\n--- ASIGNANDO RECURSOS ... ---");

        if (emergency instanceof Fire) {
            for (IEmergencyService r : available) {
                if (r instanceof Firefighters) {
                    r.attendEmergency(emergency);
                    break;
                }
            }
        } else if (emergency instanceof Accident) {
            for (IEmergencyService r : available) {
                if (r instanceof Ambulance) {
                    r.attendEmergency(emergency);
                    break;
                }
            }
        } else if (emergency instanceof Robbery) {
            for (IEmergencyService r : available) {
                if (r instanceof Police) {
                    r.attendEmergency(emergency);
                    break;
                }
            }

        }

    }

    public void attendEmergency(Emergency emergency) {

        if(emergency.isStatus()){
            System.out.println("Esta emergencia ya ha sido atendida");
            return;
        }

        emergency.startAttention();

        try {

            for (int i = 0; i <= 100 ;i++){

                if(i == 0){
                    System.out.println("Iniciando atencion de emergencia: "+ emergency.getDescription());
                } else if(i == 50){
                    System.out.println("50% de atención de emergencia");
                } else if(i == 100){
                    System.out.println("Emergencia atendida");
                }

                System.out.println("La emergencia se ha atendido en un "+i+"%");
                System.out.println();


                Thread.sleep(emergency.getResponseTime() * 10);

            }

            
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        emergency.endAttention();

        System.out.println("Emergencia atendida: "+emergency.getDescription());

        emergency.setStatus(true);

        emergenciesAttend += 1;
        totalAttentionTime += emergency.getResponseTime();

    }


    public void showStatistics() {

        System.out.println("\n--- ESTADISTICAS DEL DIA ---");
        System.out.println("Emergencias atendidas: "+emergenciesAttend);

        long mediaMs = 0;

        if(emergenciesAttend > 0){
            mediaMs = totalAttentionTime / emergenciesAttend;
        }

        double mediaSeg = mediaMs / 1000.0;

        System.out.println("Tiempo promedio de atencion: "+mediaSeg+" segundos");


        long noAttend = listEmergency.stream().filter(e -> !e.isStatus()).count();

        System.out.println("Emergencias no atendidas: "+noAttend);
    }



    public void endDay() {
        showStatistics();
        System.out.println("Guardando registro del día (simulado)...");


        
        System.out.println("Sistema preparado para siguiente ciclo.");
        
    }

    public void setStrategyPriority(StrategyPriority newStrategy) {
        this.strategyPriority = newStrategy;
    }

    public List<Emergency> getListEmergency() {
        return listEmergency;
    }

    

}
