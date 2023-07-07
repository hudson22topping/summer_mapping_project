package main.java;

public class RouteRequest {
    // preference is 0-4 as:
    // 0 means can't - imperative not to use this mode
    // 1 means can if necessary
    // 2 means prefer is possible
    // 3 means must - imperative that this mode be used
    // initialize all preferences to "preferred", 2
    int[] modePref = {2,2,2,2,2,2,2,2,0,0};
    String origin;
    String destination;
    String priority;  //QUICK, CHEAP, FREE, COMFORTABLE, PERSONALIZED, EASY, EXERCISE
    public RouteRequest() {
    }
    public void setPriority(String p){
        this.priority = p;
    }
    public String getPriority(){
        return this.priority;
    }
    public int getWalkPref() {
        return modePref[0];
    }
    public void setWalkPref(int p) {
        this.modePref[0] = p;
    }
    public int getDrivePref() {
        return modePref[1];
    }
    public void setDrivePref(int p) {
        this.modePref[1] = p;
    }
    public int getRidesharePref() {
        return modePref[2];
    }
    public void setRidesharePref(int p) {
        this.modePref[2] = p;
    }
    public int getCarRentalPref() {
        return modePref[3];
    }
    public void setCarRentalPref(int p) {
        this.modePref[3] = p;
    }
    public int getBikePref() {
        return modePref[4];
    }
    public void setBikePref(int p) {
        this.modePref[4] = p;
    }
    public int getScooterPref() {
        return modePref[5];
    }
    public void setScooterPref(int p) {
        this.modePref[5] = p;
    }
    public int getTransitPref() {
        return modePref[6];
    }
    public void setTransitPref(int p) {
        this.modePref[6] = p;
    }
    public int getBusPref() {
        return modePref[7];
    }
    public void setBusPref(int p) {
        this.modePref[7] = p;
    }
    public String getOrigin(){
        return this.origin;
    }
    public void setOrigin(String o){
        this.origin = o;
    }
    public String getDestination(){
        return this.destination;
    }
    public void setDestination(String d){
        this.destination = d;
    }
    public void generateTransitRequest() {
        setOrigin("Dunwoody Marta Station");
        setDestination("Piedmont Atlanta Hospital");
        setBikePref(1);  setBusPref(2);  setDrivePref(0);  setTransitPref(3);  setScooterPref(1);
        setCarRentalPref(0);  setRidesharePref(0); setWalkPref(2);
        setPriority("CHEAP");
    }

    public WeightedGraph getAPIWeightedGraph (Location origin, Location destination, String mode) {
        return new WeightedGraph();
    }
}
