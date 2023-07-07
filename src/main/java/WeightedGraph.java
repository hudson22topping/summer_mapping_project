package main.java;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class WeightedGraph {
    LinkedList<Vertex> vertexList;
    LinkedList<Edge> edgeList;

    static class Vertex {
        //Todo
        Location location;
        String vertexName;
        //0-walk, 1-drive, 2-rideshare, 3-carRental, 4-bicycle, 5-scooter, 6-transit, 7-bus, 8-airplane, 9-unused
        /*
         * Walking is allowed in most places with stops.  It is not allowed on the Interstate
         * Changing modes to/from driving requires a parking lot, and the cost of parking should be added to the final driven leg
         * Rideshare is allows almost anywhere.  In some places like the airport there are specific pickup areas.
         * Car Rental locations - where you can rent a car, for instance, the airport car rental lots
         * Bicycle is allowed in most places with stops, but changing to/from bike means bike rental or locking rack.
         * Biking is not allowed on 7the Interstate
         * Scooter Rental Locations - where you can rent a scooter.
         * */

        private boolean[] modes = {false, false, false, false, false, false, false, false, false, false};
        public Vertex(Location location, String uniqueNameId) {
            this.location = location;
            this.vertexName = uniqueNameId;
        }
        static public int getModeIndex (String mode) {
            switch(mode) {
                case "WALKING":
                    return 0;
                case "DRIVING":
                    return 1;
                case "RIDESHARE":
                    return 2;
                case "CARRENTAL":
                    return 3;
                case "BICYCLING":
                    return 4;
                case "SCOOTER":
                    return 5;
                case "TRANSIT":
                    return 6;
                case "BUS":
                    return 7;
                case "AIRPLANE":
                    return 8;
                default:
                    return -1;
            }
        }
        static public String getModeName (int i){
            switch(i) {
                case 0:
                    return "WALKING";
                case 1:
                    return "DRIVING";
                case 2:
                    return "RIDESHARE";
                case 3:
                    return "CARRENTAL";
                case 4:
                    return "BICYCLING";
                case 5:
                    return "SCOOTER";
                case 6:
                    return "TRANSIT";
                case 7:
                    return "BUS";
                case 8:
                    return "AIRPLANE";
                default:
                    return " ";
            }
        }

        public void autoVertexName() {
            // Get Location Name from GoogleLocation API
            //ToDo
        }
        public void setWalk(boolean tf){
            modes[0] = tf;
        }
        public void setCarPark(boolean tf){
            modes[1] = tf;
        }
        public void setRideShare(boolean tf){
            modes[2] = tf;
        }
        public void setCarRental(boolean tf){
            modes[3] = tf;
        }
        public void setBikeRack(boolean tf){
            modes[4] = tf;
        }
        public void setScooterRental(boolean tf){
            modes[5] = tf;
        }
        public void setTransit(boolean tf){
            modes[6] = tf;
        }
        public void setBusStop(boolean tf){
            modes[7] = tf;
        }
        public boolean getWalk(){
            return modes[0];
        }
        public boolean getCarPark(){
            return modes[1];
        }
        public boolean getRideShare(){
            return modes[2];
        }
        public boolean getCarRental(){
            return modes[3];
        }
        public boolean getBikeRack(){
            return modes[4];
        }
        public boolean getScooterRental(){
            return modes[5];
        }
        public boolean getTransit(){
            return modes[6];
        }
        public boolean getBusStop(){
            return modes[7];
        }

    }

    static class Edge {
        Vertex source;
        Vertex destination;
        String mode;
        Integer duration;
        Double cost;
        Integer distance;

        public Edge(Vertex source, Vertex destination, String mode, Integer duration, Double cost, Integer distance) {
            this.source = source;
            this.destination = destination;
            this.mode = mode;
            this.duration = duration;
            this.distance = distance;
            this.cost = cost;
            if (cost == 0.00d) {
                this.cost = estimateCost(this);
            }
        }
        public static double estimateCost(Edge e){
            switch(e.mode){
                case "WALKING":
                case "BICYCLING":
                    return 0.00d;
                case "DRIVING":
                    return (.90d * e.distance);
                case "RIDESHARE":
                    return (10.0d + 1.60d * e.distance);
                case "CARRENTAL":
                    return (90.0d + .20 * e.distance);
                case "SCOOTER":
                    return 1.0d + .39d * e.duration;
                case "TRANSIT":
                case "BUS":
                    return 2.50d;
                default:
                    return 0.00d;
            }
        }
    }


    public WeightedGraph() {
        this.vertexList = new LinkedList<>();
        this.edgeList = new LinkedList<>();
    }

    public Vertex addVertex(Vertex v) {
        this.vertexList.addLast(v);
        return v;
    }

    public Edge addEdge(Edge e) {
        this.edgeList.addLast(e);

        // make Vertex mode true at source and destination of the edge
        int sIndex = getVertexIndex(e.source.vertexName);
        this.vertexList.get(sIndex).modes[Vertex.getModeIndex(e.mode)] = true;
        int dIndex = getVertexIndex(e.destination.vertexName);
        this.vertexList.get(dIndex).modes[Vertex.getModeIndex(e.mode)] = true;
        return this.edgeList.getLast();
    }

    public Edge addEdge(Vertex source, Vertex destination, String mode, Integer duration, Double cost, Integer distance)
    {
        this.edgeList.addLast(new Edge(source, destination, mode, duration, cost, distance));

        // make Vertex mode true at source and destination of the edge
        int sIndex = getVertexIndex(source.vertexName);
        this.vertexList.get(sIndex).modes[Vertex.getModeIndex(mode)] = true;
        int dIndex = getVertexIndex(destination.vertexName);
        this.vertexList.get(dIndex).modes[Vertex.getModeIndex(mode)] = true;
        return this.edgeList.getLast();
        //
    }
    public void addGraph(WeightedGraph g) {
        // iterate over vertices of argument g
        ListIterator<Vertex> vIterator = (ListIterator<Vertex>) g.vertexList.iterator();
        while (vIterator.hasNext()) {
            this.vertexList.addLast(vIterator.next());
        }

        // iterate over edges of argument g
        ListIterator<Edge> eIterator = (ListIterator<Edge>) g.edgeList.iterator();
        while (eIterator.hasNext()) {
            this.edgeList.addLast(eIterator.next());
        }
    }

    public int getVertexIndex(String s) {
        ListIterator<Vertex> vertexIterator = (ListIterator<Vertex>) vertexList.iterator();
        while (vertexIterator.hasNext()) {
            if (vertexIterator.next().vertexName.contains(s)) {
                return vertexIterator.previousIndex();
            }
        }
        return -1; // return -1 if not found
    }

    public Vertex getVertex(String s) {
        int vIndex = getVertexIndex(s);
        return this.vertexList.get(vIndex);
    }

    public void printGraph(){
        Iterator<Vertex> vertexIterator = vertexList.iterator();
        while (vertexIterator.hasNext()) {
            Vertex tempVertex = vertexIterator.next();
            System.out.println(tempVertex.location.longitude + "  " + tempVertex.location.latitude + "  " +
                    tempVertex.vertexName);
        }
        Iterator<Edge> edgeIterator = edgeList.iterator();
        while (edgeIterator.hasNext()) {
            Edge tempEdge = edgeIterator.next();
            System.out.println("\n\nFrom: " + tempEdge.source.vertexName + "\nTo " + tempEdge.destination.vertexName +
                    "\nMode: " + tempEdge.mode + "\nDistance: " + tempEdge.distance +
                    "\nDuration: " + tempEdge.duration +
                    "\nCost: " + tempEdge.cost);
        }
    }
    public void loadDunwoodyVertices(){
        //Todo
    }
    public void loadKennesawVertices(){
        //Todo
    }
    public void loadHartsfieldVertices(){
        //Todo
    }

    public void loadTestGraph1(WeightedGraph graph){

        // add vertices
        graph.addVertex(new Vertex(new Location(34.0333005,-84.5788771), "KSU Kennesaw"));
        graph.addVertex(new Vertex(new Location(33.9211998,-84.3442140), "Dunwoody Marta Station"));
        graph.addVertex(new Vertex(new Location(33.9518345,-84.5442312), "National Cemetery of Marietta"));
        graph.addVertex(new Vertex(new Location(33.6323356,-84.4378869), "Hartsfield Jackson Airport"));
        graph.addVertex(new Vertex(new Location(33.8021506,-84.1539056), "Stone Mountain Park"));

        // add edges
        // for testing clarity, making each vertex a separate variable
        Vertex v1 = graph.getVertex("KSU Kennesaw");
        Vertex v2 = graph.getVertex("Dunwoody Marta Station");
        graph.addEdge(v1, v2, "WALKING", 23991, 0.00, 31766 );
        // Fare - CobbLinc route 45 is local, 1-way, $2.50 for adult, free transfer to route 10 and Marta for 3 hours.
        graph.addEdge(v1, v2, "TRANSIT", 10537, 2.50, 64413);  //this is mixed walk/transit
        graph.addEdge(v1, v2, "DRIVING", 1466, 14.48, 34286);  //cost is .68*miles
        // duration is driving plus 10 min pickup
        // cost is $10 + $1.60/mile
        graph.addEdge(v1, v2, "RIDESHARE", 2065, 45.20, 34286);
        graph.addEdge(v1, v2, "BICYCLING", 7124, 0.00, 32629);

        Vertex v3 = graph.getVertex("National Cemetery of Marietta");
        graph.addEdge(v3, v1, "TRANSIT", 1992, 2.50, 17967);
        graph.addEdge(v3, v1, "RIDESHARE", 737, 21.36,11467);
        graph.addEdge(v3, v1, "BICYCLING", 2606, 0.00, 12898);

        graph.printGraph();
    }
    public void loadTestGraphDunMacysToPiedmont(WeightedGraph graph){

        // add vertices
        Vertex v1 = graph.addVertex(new Vertex(new Location(33.9228732,-84.3418493), "Macys - Perimeter Mall"));
        Vertex v2 = graph.addVertex(new Vertex(new Location(33.921227,-84.344398), "Rail stop - Dunwoody Marta Station"));
        Vertex v3 = graph.addVertex(new Vertex(new Location(33.789112,-84.387383), "Rail stop - Arts Center Marta Station"));
        Vertex v4 = graph.addVertex(new Vertex(new Location(33.7892632,-84.3873414), "Bus stop - Arts Center Marta Station"));
        Vertex v5 = graph.addVertex(new Vertex(new Location(33.8082253,-84.3934548), "Bus stop - Peachtree Rd at Collier Rd"));
        Vertex v6 = graph.addVertex(new Vertex(new Location(33.8085817,-84.3943387), "Piedmont Hospital - Peachtree Rd"));


        // add edges
        // for testing clarity, making each vertex a separate variable

        Edge e1 = graph.addEdge(v1, v2, "WALKING", 271, 0.00, 347);
        Edge e2 = graph.addEdge(v2, v3, "TRANSIT", 900, 0.00, 17083);
        Edge e3 = graph.addEdge(v3, v4, "WALKING", 18, 0.00, 17);
        Edge e4 = graph.addEdge(v4, v5, "TRANSIT", 699, 0.00, 3083);
        Edge e5 = graph.addEdge(v5, v6, "WALKING", 103, 0.00, 121);
    }


    public static void main(String[] args) {


        WeightedGraph graph = new WeightedGraph();
        // graph.loadTestGraph1(graph);
        graph.loadTestGraphDunMacysToPiedmont(graph);
        graph.printGraph();
    }
}
