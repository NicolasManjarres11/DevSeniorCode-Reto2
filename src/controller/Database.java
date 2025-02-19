package controller;

import java.io.*;
import java.util.List;
import model.Emergency;
import utils.Gravity;

public class Database {

    private static final String database = "src\\model\\emergencies.txt";
    private static EmergencySystem emergencySystem = EmergencySystem.getInstance();
    

    public static void saveEmergencies(List<Emergency> emergencies) {

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

    public static List<Emergency> loadEmergencies(List<Emergency> emergency) throws IOException{

        File file = new File(database);
        
        if(!file.exists()) return  emergency;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))){

            String line;

            while((line = reader.readLine()) != null){

                String[] data = line.split(",");


                if(data.length == 7){
                    String type = data[0];
                    String location = data[1];
                    Gravity gravity = Gravity.valueOf(data[2]);
                    int responseTime = Integer.parseInt(data[3]);
                    boolean status = Boolean.parseBoolean(data[4]);
                    int initialAttentionTime = Integer.parseInt(data[5]);
                    int finalAttentionTime = Integer.parseInt(data[6]);
                }

            }
            
        
        }

        return emergency;

    }




}
