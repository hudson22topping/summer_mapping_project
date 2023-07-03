package src.main.java;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MARTA {
    public static void main(String[] args) {
        try {
            // Specify the MARTA API endpoint
            String endpointUrl = "http://developer.itsmarta.com/RealtimeTrain/RestServiceNextTrain/GetRealtimeArrivals?apikey=ba7b5f2c-bfd8-49a6-bcf6-11396434c8a9";
            
            // Create a URL object with the endpoint URL
            URL url = new URL(endpointUrl);
            
            // Create an HttpURLConnection object and open a connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            // Set the request method
            connection.setRequestMethod("GET");
            
            // Get the response code
            int responseCode = connection.getResponseCode();
            
            // Check if the response code is successful (200)
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Create a BufferedReader to read the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                
                // Read the response line by line
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                
                // Print the response
                System.out.println(response.toString());
            } else {
                System.out.println("Error: " + responseCode);
            }
            
            // Close the connection
            connection.disconnect();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

//YOUR KEY is: ba7b5f2c-bfd8-49a6-bcf6-11396434c8a9
