package main.java;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GeoModel {
    WeightedGraph fastestRoute = new WeightedGraph();
    WeightedGraph cheapestRoute = new WeightedGraph();
    WeightedGraph originAreaGeoMap = new WeightedGraph();
    WeightedGraph destinationAreaGeoMap = new WeightedGraph();
    WeightedGraph overallGraph = new WeightedGraph();
    List<WeightedGraph> routeList = new ArrayList<>();
    WeightedGraph.Vertex originVert;


    public GeoModel() {
    }
    public GeoModel(RouteRequest rr) {
        createGeoModel(rr.getOrigin(), rr.getDestination(), rr.getModePrefAsList());
    }


    public WeightedGraph createGeoModel(String origin, String destination, List<String> modes) {
        RouteRequest tempRequest = new RouteRequest();
        WeightedGraph coreRoute = removeExtraVerticesFromRoute
                (tempRequest.getAPIWeightedGraph(origin, destination, "transit"));
        this.originVert = coreRoute.vertexList.get(0);
        this.routeList.add(coreRoute);
        for (int i = 1; i < coreRoute.vertexList.size(); i++) {
            String legStart = coreRoute.vertexList.get(i-1).vertexName;
            String legEnd = coreRoute.vertexList.get(i).vertexName;
            for (String loopMode : modes) {
                WeightedGraph legRoute = tempRequest.getAPIWeightedGraph(legStart, legEnd, loopMode);
                legRoute = GeoModel.removeExtraVerticesFromRoute(legRoute);
                this.routeList.add(legRoute);
                this.overallGraph.addGraph(legRoute);
            }
            overallGraph = GeoModel.removeDuplicateVertices(overallGraph);
        }
        //Todo - add getLegRoutes to createGeoModel method
        return overallGraph;
    }
    public static WeightedGraph removeExtraVerticesFromRoute(WeightedGraph route) {  // considering routes non-branching
        String lastMode = "";
        int listSize = route.edgeList.size();
        for (int i = 0; i<listSize; i++) {
            if (lastMode == route.edgeList.get(i).mode) {  // do two adjacent edges have the same mode?
                WeightedGraph.Edge edge1 = route.edgeList.get(i-1);
                WeightedGraph.Edge edge2 = route.edgeList.get(i);
                edge1.distance += edge2.distance;
                edge1.duration += edge2.duration;
                edge1.cost += edge2.cost;
                edge1.end = edge2.end;
                route.edgeList.set(i-1, edge1);
                route.edgeList.remove(i); listSize--;
                route.vertexList.remove(route.getVertex(edge2.start.vertexName));
                i--;
            }
            lastMode = route.edgeList.get(i).mode;
        }
        return route;
    }
/*    public WeightedGraph createRouteFromApi(String origin, String destination) {
        //Todo - Test and Confirm
        RouteRequest transitRoutes = new RouteRequest();
        WeightedGraph transitMap = transitRoutes.getAPIWeightedGraph(origin, destination, "TRANSIT");
        transitMap = removeExtraVerticesFromRoute(transitMap);
        return transitMap;
    } */

    public static WeightedGraph removeDuplicateVertices(WeightedGraph graph) {
 /*     // This version is not dependent on order, but way less efficient and more complex.  Save in case
        // needed for combined weighted graphs that are not linear.
        */
        // Remove duplicate vertices and reassign edges to kept vertex
        int vLSize = graph.vertexList.size();
        for (int i = 0; i < vLSize; i++) {
            for (int j = i + 1; j < vLSize; j++) {
                if (graph.vertexList.get(i).isMatch(graph.vertexList.get(j))) {
                    // redirect edges from vert j to vert i so you can delete vertex j
                    // delete edges between the two matched vertices
                    int eLSize = graph.edgeList.size();
                    for (int k = 0; k < eLSize; k++) {
                        if (graph.edgeList.get(k).start.isMatch(graph.vertexList.get(j))) {
                            graph.edgeList.get(k).start = graph.vertexList.get(i);
                        }
                        if (graph.edgeList.get(k).end.isMatch(graph.vertexList.get(j))) {
                            graph.edgeList.get(k).end = graph.vertexList.get(i);
                        }
                        if (graph.edgeList.get(k).start.isMatch(graph.edgeList.get(k).end)) {
                            graph.edgeList.remove(k);
                            eLSize--;
                            k--;
                        }
                    }
                    graph.vertexList.remove(j);
                    vLSize--;
                    j--;
                }
            }
        }
        // Remove duplicate edges
        int eLSize = graph.edgeList.size();
        for (int i = 0; i < eLSize; i++) {
            for (int j = i + 1; j < eLSize; j++) {
                if (graph.edgeList.get(i).mode == (graph.edgeList.get(j).mode)) {
                    // reduce repeated lookups with local variables
                    WeightedGraph.Edge edge1 = graph.edgeList.get(i);
                    WeightedGraph.Edge edge2 = graph.edgeList.get(j);
                    if (edge1.start.isMatch(edge2.start) && (edge1.end.isMatch(edge2.end))) {
                        graph.edgeList.remove(j);  eLSize--; j--;
                    }
                    if (edge1.start.isMatch(edge2.end) && (edge1.end.isMatch(edge1.start))) {
                        graph.edgeList.remove(j);  eLSize--; j--;
                    }
                }
            }
        }
        return graph;
    }
}
