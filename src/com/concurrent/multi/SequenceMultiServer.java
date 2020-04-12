package com.concurrent.multi;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SequenceMultiServer {
    public static void main(String[] args) {
        //set port variable
        int port = 4444;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("MultiServer started on port: " + serverSocket.getLocalPort());
            while (true) {
                Socket clientSocket;
                try {
                    //create connection to client
                    clientSocket = serverSocket.accept();
                } catch (IOException e) {
                    System.err.println("Accept failed: " + 4444 + ", " + e.getMessage());
                    continue;
                }
                //create new thread with socket connection and pass socket into thread
                new SequenceMultiServerThread(clientSocket).start();
            }

        } catch (IOException e) {
            System.err.println("Could not listen on port: " + 4444 + ", " + e.getMessage());
        }
    }
}
