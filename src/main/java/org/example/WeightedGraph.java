import java.util.*;

public class WeightedGraph {
    private Map<Location, List<Edge>> graph;

    public WeightedGraph() {
        graph = new HashMap<>();
    }

    public void addEdge(Location source, Location destination, String name, int distance, double duration, int cost) {
        Edge edge = new Edge(destination, name, distance, duration, cost);
        graph.computeIfAbsent(source, k -> new ArrayList<>()).add(edge);
    }

    public void printGraph() {
        for (Map.Entry<Location, List<Edge>> entry : graph.entrySet()) {
            Location location = entry.getKey();
            List<Edge> edges = entry.getValue();

            System.out.println("From: " + location.getName());
            for (Edge edge : edges) {
                System.out.println("To: " + edge.getDestination().getName());
                System.out.println("Name: " + edge.getName());
                System.out.println("Distance: " + edge.getDistance());
                System.out.println("Duration: " + edge.getDuration());
                System.out.println("Cost: " + edge.getCost());
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        WeightedGraph graph = new WeightedGraph();

        Location atlanta = new Location("Atlanta");
        Location marietta = new Location("Marietta");
        Location alpharetta = new Location("Alpharetta");

        graph.addEdge(atlanta, marietta, "I-75", 20, 30.0, 5);
        graph.addEdge(atlanta, alpharetta, "GA-400", 25, 35.0, 8);
        graph.addEdge(marietta, alpharetta, "I-285", 15, 25.0, 6);

        graph.printGraph();
    }

    private static class Edge {
        private Location destination;
        private String name;
        private int distance;
        private double duration;
        private int cost;

        public Edge(Location destination, String name, int distance, double duration, int cost) {
            this.destination = destination;
            this.name = name;
            this.distance = distance;
            this.duration = duration;
            this.cost = cost;
        }

        public Location getDestination() {
            return destination;
        }

        public String getName() {
            return name;
        }

        public int getDistance() {
            return distance;
        }

        public double getDuration() {
            return duration;
        }

        public int getCost() {
            return cost;
        }
    }

    private static class Location {
        private String name;

        public Location(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
