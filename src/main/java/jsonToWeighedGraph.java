import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import src.main.java.WeightedGraph;


public class testJsonToWeightedGraph {
    public static void main(String[] args) {
        // Specify the path to the walking directions JSON file
        String walkingDirectionsFilePath = "src/main/data/walking_directions.json";

        try {
            // Read the JSON file content
            String walkingDirectionsJson = readJsonFile(walkingDirectionsFilePath);

            // Parse the JSON and construct the weighted graph
            WeightedGraph weightedGraph = constructWeightedGraph(walkingDirectionsJson);

            // Output the weighted graph to the console
            System.out.println("Weighted Graph:");
            weightedGraph.printGraph();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readJsonFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return new String(Files.readAllBytes(path));
    }

    private static WeightedGraph constructWeightedGraph(String json) {
        WeightedGraph weightedGraph = new WeightedGraph();

        JSONObject directionsJson = new JSONObject(json);
        JSONArray routesArray = directionsJson.getJSONArray("routes");
        if (routesArray.length() > 0) {
            JSONObject route = routesArray.getJSONObject(0);

            JSONArray legsArray = route.getJSONArray("legs");
            for (int i = 0; i < legsArray.length(); i++) {
                JSONObject leg = legsArray.getJSONObject(i);

                JSONArray stepsArray = leg.getJSONArray("steps");
                for (int j = 0; j < stepsArray.length(); j++) {
                    JSONObject step = stepsArray.getJSONObject(j);

                    int duration = step.getJSONObject("duration").getInt("value");
                    int distance = step.getJSONObject("distance").getInt("value");

                    // Construct the source vertex
                    Vertex source = new Vertex(
                            new Location(0.0, 0.0), // Provide the appropriate latitude and longitude
                            "SourceVertexName",   // Provide the appropriate vertex name
                            new boolean[10]       // Provide the appropriate modes array size
                    );

                    // Construct the destination vertex
                    Vertex destination = new Vertex(
                            new Location(0.0, 0.0), // Provide the appropriate latitude and longitude
                            "DestinationVertexName",  // Provide the appropriate vertex name
                            new boolean[10]           // Provide the appropriate modes array size
                    );

                    String mode = "WALKING"; // Assuming all steps are for walking mode

                    weightedGraph.addEdge(source, destination, mode, duration, 0.0, distance);
                }
            }
        }

        return weightedGraph;
    }

}
