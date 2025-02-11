package model.observer;

import model.Emergency;

public interface SubjectEmergency {

    void addObserver(ObserverEmergency observerEmergency);
    void removeObserver(ObserverEmergency observerEmergency);
    void notifyObservers(Emergency emergency);

}
