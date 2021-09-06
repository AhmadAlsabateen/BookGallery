package databaseConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gallery.query.Query;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class DatabaseConnection {
    private static final int PORT = 80;
    private static final String IP = "db-service-entrypoint";
    ObjectMapper objectMapper;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    private DatabaseConnection() {
        try {
            clientSocket = new Socket(IP, PORT);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            objectMapper = new ObjectMapper();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DatabaseConnection getInstance() {
        return new DatabaseConnection();
    }

    public String executeQuery(Query query) {
        try {
            String request = objectMapper.writeValueAsString(query);
            out.println(request);
            String response = in.readLine();
            clientSocket.close();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


}
