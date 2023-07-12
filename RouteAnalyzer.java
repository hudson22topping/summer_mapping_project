package main.java;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
public class RouteAnalyzer {
    WeightedGraph routeModel = new WeightedGraph();
    RouteRequest userRequest = new RouteRequest();

    public RouteAnalyzer(WeightedGraph routeModel, RouteRequest userRequest) {
        this.routeModel = routeModel;
        this.userRequest = userRequest;
    }

    public RouteAnalyzer() {
        this.routeModel = new WeightedGraph();
        this.userRequest = new RouteRequest();
    }

    public LinkedList<WeightedGraph> findRoutes(){
        LinkedList<WeightedGraph> routeOptions = new LinkedList<>();
        Boolean hasMandatedMode = Boolean.FALSE;
        for(int i = 0; i < userRequest.modePref.length; i++) {
            if (userRequest.modePref[i] == 3) {
                routeOptions.add(getBest1ModeRoute(userRequest.modePref[i], routeModel, userRequest.priority));
                hasMandatedMode = Boolean.TRUE;
            }
        }
        if (routeOptions.size() > 2) { // no need for additional analysis
            return routeOptions;
        } else {
//            getBestGeneralRoute
// todo - finish findRoutes method
        }
        return routeOptions;
    }

    private WeightedGraph getBest1ModeRoute(int mode, WeightedGraph geoModel, String priority) {
        // Todo - complete getBest1ModeRoute
        return new WeightedGraph();
    }
}
