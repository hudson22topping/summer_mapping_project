package main.java;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class ApiConnector {
    String apiKey = "AIzaSyBOpkaRmOY382_oPW5cJlcFHc8d7pLdHfg";
    String url;
    String resultText;

    public ApiConnector(String origin, String destination, String mode){
        this.url = buildDirectionsUrl(origin, destination, mode, apiKey);
    }

    private static String buildDirectionsUrl(String origin, String destination, String mode, String apiKey) {
        return "https://maps.googleapis.com/maps/api/directions/json?" +
                "origin=" + origin.replace(" ", "+") +
                "&destination=" + destination.replace(" ", "+") +
                "&mode=" + mode +
                "&key=" + apiKey;
    }

    public String saveJsonToString() {
        URL apiEndpoint = null;
        String jsonText;
        HttpURLConnection connection = null;
        try {
            apiEndpoint = new URL(this.url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        try {
            connection = (HttpURLConnection) apiEndpoint.openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (InputStream inputStream = connection.getInputStream()) {
            jsonText = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        connection.disconnect();
        return jsonText;
    }

    public WeightedGraph constructWeightedGraph(String json) {
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

                    String startVertexName = step.getJSONObject("start_location").toString();
                    String endVertexName = step.getJSONObject("end_location").toString();

                    // Add the start vertex if it doesn't exist
                    if (weightedGraph.getVertex(startVertexName) == null) {
                        weightedGraph.addVertex(new WeightedGraph.Vertex(new Location(0.0, 0.0), startVertexName));
                    }
                    // Add the end vertex if it doesn't exist
                    if (weightedGraph.getVertex(endVertexName) == null) {
                        weightedGraph.addVertex(new WeightedGraph.Vertex(new Location(0.0, 0.0), endVertexName));
                    }

                    // Get the existing start and end vertices
                    WeightedGraph.Vertex source = weightedGraph.getVertex(startVertexName);
                    WeightedGraph.Vertex destination = weightedGraph.getVertex(endVertexName);

                    // Check if vertices are found
                    if (source != null && destination != null) {
                        String mode = "WALKING"; // Assuming all steps are for walking mode
                        weightedGraph.addEdge(source, destination, mode, duration, 0.0, distance);
                    } else {
                        // Handle case when source or destination vertex is not found
                        System.out.println("Source or destination vertex not found: " + startVertexName + ", " + endVertexName);
                    }
                }
            }
        }

        return weightedGraph;
    }

}
