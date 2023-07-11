package main.java;

import java.util.Arrays;
import java.util.List;
public class RouteAnalyzer {
    WeightedGraph geoModel = new WeightedGraph();
    RouteRequest userRequest = new RouteRequest();

    public RouteAnalyzer(WeightedGraph geoModel, RouteRequest userRequest) {
        this.geoModel = geoModel;
        this.userRequest = userRequest;
    }

    public RouteAnalyzer() {
        this.geoModel = new WeightedGraph();
        this.userRequest = new RouteRequest();
    }
    public WeightedGraph findRoutes(RouteRequest r, WeightedGraph w){

        for(int i = 0; i < r.modePref.length; i++) {
            if (r.modePref[i] == 3){
                this.analyzeModel(r.modePref[i], w);
            }


        }


        return new WeightedGraph();
    }

    private void analyzeModel(int mode, WeightedGraph geoModel) {

    }
}
