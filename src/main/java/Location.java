package main.java;

public class Location {
    double longitude;
    double latitude;


    public Location(double la, double lo) {
        this.longitude = lo;
        this.latitude = la;
    }
    public boolean isMatch(Location tempLoc) {
        // match rounded to 4 decimal places - about 10 meter, so 20 meter square box around location
        if (Math.round(tempLoc.getLatitude() * 10000) == Math.round(this.getLatitude() * 10000)){
            if (Math.round(tempLoc.getLongitude() * 10000) == Math.round(this.getLongitude() * 10000)){
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
