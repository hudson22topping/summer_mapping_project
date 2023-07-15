package main.java;

public class Location {
    double longitude;
    double latitude;


    public Location(double la, double lo) {
        this.longitude = lo;
        this.latitude = la;
    }
    public boolean isMatch(Location tempLoc) {
        // .0001 degrees is about 10 meters, so 20 meter square box around location
        if (Math.abs(tempLoc.getLatitude() - this.getLatitude()) < .0001){
            if (Math.abs(tempLoc.getLongitude() - this.getLongitude()) < .0001){
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }


}
