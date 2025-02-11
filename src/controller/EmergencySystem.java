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
import model.strategy.StrategyPriority;

public class EmergencySystem implements SubjectEmergency{

    private static EmergencySystem instance;
    private List<Emergency> listEmergency;
    private List<IEmergencyService> listResources;
    private List<ObserverEmergency> observers;

    private StrategyPriority strategyPriority;

    private int emergenciesAttend;
    private long totalAttentionTime;

    private EmergencySystem() {

        strategyPriority = new StrategyPriority();
        listEmergency = new ArrayList<>();
        listResources = new ArrayList<>();
        observers = new ArrayList<>();
        emergenciesAttend = 0;
        totalAttentionTime = 0;

    }

    //Se instancia una vez el sistema de emergencias

    public static EmergencySystem getInstance(){
        if(instance == null){
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

    public void registerResource(IEmergencyService resource){
        listResources.add(resource);
    }

    public void showResourcesStatus(){
        System.out.println("\n--- ESTADO ACTUAL DE LOS RECURSOS ---");
        for(IEmergencyService resource : listResources){
            System.out.println(resource.toString());
        }
    }

    //Lambda

    public List<IEmergencyService> filterAvailableResources(){
        return listResources.stream().filter(r -> r.isStatus()).collect(Collectors.toList());
    }

    public void addEmergency(Emergency emergency){
        listEmergency.add(emergency);
        notifyObservers(emergency);
    }

     public List<Emergency> getEmergencies(){
        return listEmergency.stream().filter(e -> !e.isStatus()).collect(Collectors.toList());
     }

     public void assignResourcesToEmergency(Emergency emergency){

        List<IEmergencyService> available = filterAvailableResources();

        if(available.isEmpty()){
            System.out.println("No hay recursos disponibles para atender la emergencia");
            return;
        }
        System.out.println("\n--- ASIGNANDO RECURSOS ... ---");

        if(emergency instanceof  Fire){
            for (IEmergencyService r : available){
                if (r instanceof Firefighters){
                    r.attendEmergency(emergency);
                    break;
                }
            }
        } else if(emergency instanceof Accident){
            for (IEmergencyService r : available){
                if (r instanceof Ambulance){
                    r.attendEmergency(emergency);
                    break;
                }
            }
        } else if (emergency instanceof Robbery){
            for (IEmergencyService r : available){
                if (r instanceof Police){
                    r.attendEmergency(emergency);
                    break;
                }
            }

        }

        public void attendEmergency(Emergency emergency){

        }



     }





}
