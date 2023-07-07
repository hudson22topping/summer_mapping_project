package main.java;

import java.util.ArrayList;
import java.util.List;

public class geoModel {
    WeightedGraph fastestRoute = new WeightedGraph();
    WeightedGraph cheapestRoute = new WeightedGraph();
    WeightedGraph originAreaGeoMap = new WeightedGraph();
    WeightedGraph destinationAreaGeoMap = new WeightedGraph();
    List<WeightedGraph> routeList = new ArrayList<>();


    public geoModel() {
    }
    public void createGeoMap(Location area, Integer radiusMeters){

    }
}
