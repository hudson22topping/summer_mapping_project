package main.java;

import java.util.ArrayList;
import java.util.List;

public class GeoModel {
    WeightedGraph fastestRoute = new WeightedGraph();
    WeightedGraph cheapestRoute = new WeightedGraph();
    WeightedGraph originAreaGeoMap = new WeightedGraph();
    WeightedGraph destinationAreaGeoMap = new WeightedGraph();
    List<WeightedGraph> routeList = new ArrayList<>();


    public GeoModel() {
    }
    public WeightedGraph createGeoMap(Location area, Integer radiusMeters){
        //Todo
        return new WeightedGraph();
    }
    public WeightedGraph createGeoMap(Location origin, Location destination, List<String> modes){
        //Todo
        return new WeightedGraph();
    }
    public WeightedGraph combineAjacentEdgesWithSameMode (WeightedGraph route) {
        String lastMode = "";
        int listSize = route.edgeList.size();
        for (int i = 0; i<listSize; i++) {
            if (lastMode == route.edgeList.get(i).mode) {
                WeightedGraph.Edge edge1 = route.edgeList.get(i-1);
                WeightedGraph.Edge edge2 = route.edgeList.get(i);
                edge1.distance += edge2.distance;
                edge1.duration += edge2.duration;
                edge1.cost += edge2.cost;
                edge1.end = edge2.end;
                route.edgeList.set(i-1, edge1);
                route.edgeList.remove(i);
                i--;
            }
        }
        return route;
    }
    public WeightedGraph createGeoMap(Location origin, Location destination){
        //Todo - Test and Confirm
        RouteRequest transitRoutes = new RouteRequest();
        WeightedGraph transitRaw = transitRoutes.getAPIWeightedGraph(origin, destination, "TRANSIT");
        WeightedGraph transitClean = transitRaw;
        transitClean = combineAjacentEdgesWithSameMode(transitRaw);
        WeightedGraph keyNodesInGeoMap = new WeightedGraph();
        for (int i = 1; i < transitClean.)



 /*       for (WeightedGraph.Vertex vertex : transitRaw.vertexList){
            // vertex is not node if it is the route origin or the destination
            if (vertex.location != origin && vertex.location != destination){
                String tempMode = "";
                // Identify edges that touch the vertex
                for (WeightedGraph.Edge edge : transitRaw.edgeList){
                    if (edge.start.location == vertex.location || edge.end.location == vertex.location){
                        // if the vertex has more than one edge mode
                        if (tempMode == "" || tempMode == edge.mode) {
                            tempMode = edge.mode;
                        }
                        else{ // this is the second mode for this vertex
                            keyNodesInGeoMap.addVertex(vertex);
                        }
                    }
                }
            }
        } */
        for (WeightedGraph.Edge edge : transitRaw.edgeList) {
            if (keyNodesInGeoMap.vertexList[keyNodesInGeoMap.getVertexIndex()])
        }
        //Todo - Add edges to geomap
        return keyNodesInGeoMap;
    }

/* Todo - confirm that this if statement works, pointer vs. values, rounding, GoogleDirections change, etc.
Notes, add vertex to key nodes list (locations where you change transportation mode) only if it is not already in the
list, are not the origin or destination, and the edges show both transit and some other mode of transportation.
 */

            }
        }
        return new WeightedGraph();
    }
    public WeightedGraph createGeoMap(Location origin, Location destination, String priority){
        //Todo
        return new WeightedGraph();
    }
}
