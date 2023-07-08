import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser {
    public static void main(String[] args) {
        String apiKey = "YOUR_API_KEY";
        String departureAddress = "121 Baker St NW, Atlanta, GA 30313";
        String destinationAddress = "2450 Lower Roswell Rd, Marietta, GA 30068";

        try {
            // Driving directions
            String drivingUrl = buildDirectionsUrl(departureAddress, destinationAddress, "driving", apiKey);
            String directionsJson = fetchDirectionsJson(drivingUrl);
            WeightedGraph graph = constructWeightedGraph(directionsJson);
            graph.printGraph();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String buildDirectionsUrl(String origin, String destination, String mode, String apiKey) {
        return "https://maps.googleapis.com/maps/api/directions/json?" +
                "origin=" + origin.replace(" ", "+") +
                "&destination=" + destination.replace(" ", "+") +
                "&mode=" + mode +
                "&key=" + apiKey;
    }

    private static String fetchDirectionsJson(String url) throws IOException {
        URL apiEndpoint = new URL(url);

        HttpURLConnection connection = (HttpURLConnection) apiEndpoint.openConnection();
        InputStream inputStream = connection.getInputStream();
        String directionsJson = convertInputStreamToString(inputStream);
        connection.disconnect();

        return directionsJson;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        byte[] buffer = new byte[4096];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            stringBuilder.append(new String(buffer, 0, bytesRead));
        }

        return stringBuilder.toString();
    }

    private static WeightedGraph constructWeightedGraph(String directionsJson) {
        WeightedGraph graph = new WeightedGraph();

        try {
            JSONObject json = new JSONObject(directionsJson);
            JSONArray routes = json.getJSONArray("routes");
            JSONObject route = routes.getJSONObject(0);
            JSONArray legs = route.getJSONArray("legs");
            JSONObject leg = legs.getJSONObject(0);
            JSONArray steps = leg.getJSONArray("steps");

            for (int i = 0; i < steps.length(); i++) {
                JSONObject step = steps.getJSONObject(i);
                JSONObject startLocation = step.getJSONObject("start_location");
                double startLat = startLocation.getDouble("lat");
                double startLng = startLocation.getDouble("lng");

                JSONObject endLocation = step.getJSONObject("end_location");
                double endLat = endLocation.getDouble("lat");
                double endLng = endLocation.getDouble("lng");

                graph.addVertex(new Vertex(startLat, startLng));
                graph.addVertex(new Vertex(endLat, endLng));

                int duration = step.getJSONObject("duration").getInt("value");
                int distance = step.getJSONObject("distance").getInt("value");

                // You can adjust the weights based on your preference or specific requirements
                double weight = duration / distance;

                graph.addEdge(new Edge(new Vertex(startLat, startLng), new Vertex(endLat, endLng), weight));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return graph;
    }
}
