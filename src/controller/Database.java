package controller;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Emergency;
import model.factory.FactoryEmergency;
import utils.Gravity;
import utils.TypeEmergency;

public class Database {

    private static final String database = "src\\model\\emergencies.txt";
    private static EmergencySystem emergencySystem = EmergencySystem.getInstance();
    

    public void saveEmergencies(List<Emergency> emergencies) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(database))) {

            for (Emergency e : emergencies) {
                if (!e.isStatus()) {
                    writer.write(e.getType() + ","
                            + e.getLocation() + ","
                            + e.getGravity() + ","
                            + e.getResponseTime() + ","
                            + e.isStatus() + ","
                            + e.getInitialAttentionTime() + ","
                            + e.getFinalAttentionTime());
                    writer.newLine();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<Emergency> loadEmergencies() throws IOException{

        List<Emergency> emergency = emergencySystem.getEmergencies();
        File file = new File(database);
        
        if(!file.exists()) return  emergency;


        //Map para cpnvertir los valores de String a TypeEmergency

        Map<String, TypeEmergency> typeMap = new HashMap<>();

        typeMap.put("Incendio", TypeEmergency.FIRE);
        typeMap.put("Accidente", TypeEmergency.ACCIDENT);
        typeMap.put("Robo", TypeEmergency.ROBBERY);


        try (BufferedReader reader = new BufferedReader(new FileReader(file))){

            String line;

            while((line = reader.readLine()) != null){

                String[] data = line.split(",");


                if(data.length == 7){
                    TypeEmergency type = typeMap.get(data[0]);
                    if(type == null){
                        System.out.println("El tipo de emergencia no es valido.");
                        continue;
                    }

                    String location = data[1];
                    Gravity gravity = Gravity.valueOf(data[2]);
                    int responseTime = Integer.parseInt(data[3]);
                    boolean status = Boolean.parseBoolean(data[4]);
                    int initialAttentionTime = Integer.parseInt(data[5]);
                    int finalAttentionTime = Integer.parseInt(data[6]);
                    
                    emergency.add(FactoryEmergency.createEmergency(type, location, gravity, responseTime));
                }

            }
        
        } catch (IOException e) {
            e.printStackTrace();
        }

        return emergency;

    }


}
