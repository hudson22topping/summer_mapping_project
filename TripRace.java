package main.java;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class TripRace {
    public static void main(String[] args) {
        String apiKey = "AIzaSyBOpkaRmOY382_oPW5cJlcFHc8d7pLdHfg";
        String departureAddress = "121 Baker St NW, Atlanta, GA 30313";
        String destinationAddress = "2450 Lower Roswell Rd, Marietta, GA 30068";

        try {
            // Driving directions
            String drivingUrl = buildDirectionsUrl(departureAddress, destinationAddress, "driving", apiKey);
            saveDirectionsToJson(drivingUrl, "src/main/data/driving_directions.json");

            // Walking directions
            String walkingUrl = buildDirectionsUrl(departureAddress, destinationAddress, "walking", apiKey);
            saveDirectionsToJson(walkingUrl, "src/main/data/walking_directions.json");

            // Public transportation directions
            String transitUrl = buildDirectionsUrl(departureAddress, destinationAddress, "transit", apiKey);
            saveDirectionsToJson(transitUrl, "src/main/data/transit_directions.json");

            System.out.println("Directions saved to JSON files.");
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

    private static void saveDirectionsToJson(String url, String filePath) throws IOException {
        URL apiEndpoint = new URL(url);

        HttpURLConnection connection = (HttpURLConnection) apiEndpoint.openConnection();
        InputStream inputStream = connection.getInputStream();
        saveResponseToFile(inputStream, filePath);
        connection.disconnect();
    }

    private static void saveResponseToFile(InputStream inputStream, String filePath) throws IOException {
        Path outputPath = Path.of(filePath);

        try (BufferedWriter writer = Files.newBufferedWriter(outputPath, StandardOpenOption.CREATE)) {
            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                writer.write(new String(buffer, 0, bytesRead));
            }
        }
    }
}
