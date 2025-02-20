package model;


import utils.Gravity;

public abstract class Emergency {

    private String type;
    private String location;
    private Gravity gravity;
    private int responseTime;
    private boolean status;
    private long initialAttentionTime;
    private long finalAttentionTime;

    public Emergency(String type, String location, Gravity gravity, int responseTime, boolean status,
            long initialAttentionTime, long finalAttentionTime) {
        this.type = type;
        this.location = location;
        this.gravity = gravity;
        this.responseTime = responseTime;
        this.status = status;
        this.initialAttentionTime = initialAttentionTime;
        this.finalAttentionTime = finalAttentionTime;
    }

    public Emergency(String type, String location, Gravity gravity, int responseTime, boolean status) {

        this(type, location, gravity, responseTime, status, responseTime, responseTime);

    }

    public Emergency(String type, String location, Gravity gravity, int responseTime) {
        this.type = type;
        this.location = location;
        this.gravity = gravity;
        this.responseTime = responseTime;

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Gravity getGravity() {
        return gravity;
    }

    public void setGravity(Gravity gravity) {
        this.gravity = gravity;
    }

    public int getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(int responseTime) {
        this.responseTime = responseTime;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public long getInitialAttentionTime() {
        return initialAttentionTime;
    }

    public void setInitialAttentionTime(long initialAttentionTime) {
        this.initialAttentionTime = initialAttentionTime;
    }

    public long getFinalAttentionTime() {
        return finalAttentionTime;
    }

    public void setFinalAttentionTime(long finalAttentionTime) {
        this.finalAttentionTime = finalAttentionTime;
    }


    public void startAttention() {
        this.initialAttentionTime = System.currentTimeMillis();
    }

    public void endAttention() {
        this.finalAttentionTime = System.currentTimeMillis();
    }

    public long getAttentionTime() {
        return finalAttentionTime - initialAttentionTime;
    }

    public String getDescription(){
        return String.format("%s en %s con gravedad %s ",type,location, gravity);
    }

    @Override
    public String toString(){

        return getDescription() + "Tiempo estimado :" + responseTime + " minutos";

    }

    
    

    

    



}
