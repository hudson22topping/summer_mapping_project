package main.java;

public class Location {
    double longitude;
    double latitude;


    public Location(double la, double lo) {
        this.longitude = lo;
        this.latitude = la;
    }


    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }


}
