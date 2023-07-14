package main.java;

import java.util.*;

import main.java.WeightedGraph;



public class RouteAnalyzer {

    static WeightedGraph routeModel = new WeightedGraph();
    RouteRequest userRequest = new RouteRequest();


    public RouteAnalyzer(WeightedGraph routeModel, RouteRequest userRequest) {
        this.routeModel = routeModel;
        this.userRequest = userRequest;
    }

    public LinkedList<WeightedGraph> findRoutes() {
        LinkedList<WeightedGraph> routeOptions = new LinkedList<>();
        boolean hasMandatedMode = false;
        for (int i = 0; i < userRequest.modePref.length; i++) {
            if (userRequest.modePref[i] == 3) {
                routeOptions.add((WeightedGraph) getBest1ModeRoute(userRequest.modePref[i], routeModel, userRequest.priority));
                hasMandatedMode = true;
            }
        }
        if (routeOptions.size() > 2) {
            // No need for additional analysis
            return routeOptions;
        } else {
            // getBestGeneralRoute
            // Todo - Implement the logic to get the best general route based on user preferences and priority
            routeOptions.addAll(getMultiModalBestPath(routeModel, userRequest.modePref, userRequest.origin, userRequest.destination));

        }
        return routeOptions;
    }

    private List<WeightedGraph> getBest1ModeRoute(int mode, WeightedGraph geoModel, String priority) {
        String mandatedMode = WeightedGraph.Edge.getMode(mode);
    //TODO remove priority
        // Create a copy of the geoModel
        WeightedGraph weightedGraphCopy = new WeightedGraph();
        weightedGraphCopy.addGraph(geoModel);

        // Remove edges that are incompatible with the mandated mode
        List<WeightedGraph.Edge> incompatibleEdges = new ArrayList<>();
        for (WeightedGraph.Edge edge : weightedGraphCopy.edgeList) {
            if (!edge.getMode().equals(mandatedMode)) {
                incompatibleEdges.add(edge);
            }
        }
        weightedGraphCopy.edgeList.removeAll(incompatibleEdges);

        // Get the origin and destination from the user request
        WeightedGraph.Vertex originVert;
        originVert = geoModel.vertexList.get(0);

        WeightedGraph.Vertex destinationVert;
        destinationVert = geoModel.vertexList.get(geoModel.vertexList.size()-1);

        String destination = userRequest.destination;

    // Find the best path in the modified weighted graph
        List<WeightedGraph> bestPaths = weightedGraphCopy.getMultiModalBestPath(originVert, destinationVert, Collections.singletonList(mandatedMode));

        // Return the best paths with the mandated mode
        return bestPaths;
    }
/*
    private List<WeightedGraph.Path> getBestGeneralRoute(WeightedGraph geoModel, int[] modePref, String priority) {
        List<WeightedGraph.Path> bestRoute = new ArrayList<>();
        double bestCost = Double.POSITIVE_INFINITY;

        // Iterate over all vertices in the geoModel
        for (WeightedGraph.Vertex startVertex : geoModel.vertexList) {
            // Find the best paths from the current start vertex to all other vertices
            List<WeightedGraph.Path> paths = geoModel.getBestPaths(startVertex.vertexName, null, null);

            // Sort the paths based on the user's priority
            sortPathsByPriority(paths, priority);

            // Check if the current best path is better than the previously found best route
            if (!paths.isEmpty() && paths.get(0).getTotalCost() < bestCost) {
                // Update the best route and cost
                bestRoute = new ArrayList<>(paths);
                bestCost = paths.get(0).getTotalCost();
            }
        }
        return bestRoute;
    }*/


    private List<WeightedGraph.Path> createPathsFromPath(WeightedGraph.Path path) {
        List<WeightedGraph.Path> paths = new ArrayList<>();
        paths.add(path);
        return paths;
    }
    public static List<WeightedGraph> getMultiModalBestPath(WeightedGraph weightedGraph, int[] modePref, String origin, String destination) {
        List<String> availableModes = new ArrayList<>();

        // Map the mode preferences to the corresponding modes
        for (int i = 0; i < modePref.length; i++) {
            if (modePref[i] > 0) {
                String mode = WeightedGraph.Edge.getMode(i);
                availableModes.add(mode);
            }
        }

        // Find the best paths through the weighted graph using multi-modal approach
        //WeightedGraph.Vertex startVertex = weightedGraph.getVertex(origin);
        //WeightedGraph.Vertex endVertex = weightedGraph.getVertex(destination);

        Set<WeightedGraph.Vertex> visited = new HashSet<>();
        List<WeightedGraph> bestPaths = new ArrayList<>();

        //WeightedGraph.Vertex startVertex;
        WeightedGraph.Vertex startVertex = routeModel.vertexList.get(0);

        WeightedGraph.Vertex endVertex;
        endVertex = routeModel.vertexList.get(routeModel.vertexList.size()-1);

        weightedGraph.findMultiModalPaths(startVertex, endVertex, availableModes, new WeightedGraph(), visited, bestPaths);

        return bestPaths;
    }

    private static List<String> getAvailableModes(UserAccount userAccount) {
        List<String> availableModes = new ArrayList<>();

        if (userAccount.getWalkPref() > 0) {
            availableModes.add("walking");
        }
        if (userAccount.getDrivePref() > 0) {
            availableModes.add("driving");
        }
        if (userAccount.getRidesharePref() > 0) {
            availableModes.add("rideshare");
        }
        if (userAccount.getCarRentalPref() > 0) {
            availableModes.add("carrental");
        }
        if (userAccount.getBikePref() > 0) {
            availableModes.add("bicycling");
        }
        if (userAccount.getScooterPref() > 0) {
            availableModes.add("scooter");
        }
        if (userAccount.getTransitPref() > 0) {
            availableModes.add("transit");
        }
        if (userAccount.getBusPref() > 0) {
            availableModes.add("bus");
        }
        if (userAccount.getFlightPref() > 0) {
            availableModes.add("airplane");
        }

        return availableModes;
    }

    private static void sortPathsByPriority(List<WeightedGraph.Path> paths, String priority) {
        paths.sort((path1, path2) -> {
        // Implement the sorting logic based on the user's priority
        // Compare path1 and path2 based on priority and return -1, 0, or 1 accordingly

        // Example sorting by total cost (ascending order)
        double cost1 = path1.getTotalCost();
        double cost2 = path2.getTotalCost();
        if (cost1 < cost2) {
            return -1;
        } else if (cost1 > cost2) {
            return 1;
        } else {
            return 0;
        }
    });
}


}


