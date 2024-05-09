package socketServer;


import socketServer.controller.SocketController;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("Server/app.properties"));
//
//        DatabaseConnection databaseConnection = new DatabaseConnection(properties);
//        CarRepository carRepository = new CarRepositoryDbImpl(databaseConnection);

        SocketController socketController = new SocketController(properties);
        socketController.listenForClients();
    }
}