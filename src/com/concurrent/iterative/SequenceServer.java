package com.concurrent.iterative;

import com.concurrent.shared.LongestSequence;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class SequenceServer {
    public static void main(String[] args) {
        //set port variable
        int port = 4000;
        //instantiate class used to do sequence calculations
        LongestSequence longestSequence = new LongestSequence();
        //print port server is running on
        System.out.println("Server is listening on port " + port);

        //try with resources on creating a ServerSocket with port 4000
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            //instantiate socket and streams
            Socket socket = serverSocket.accept();
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            System.out.println("Received new connection.");

            //while connection is active
            while (true) {
                //instantiate list with user values added on every input
                List<Integer> list = longestSequence.getInput(input, output);

                //if list is empty return text and close connection
                if (list.isEmpty()) {
                    output.println("List was empty, closing connection.");
                    output.flush();
                    break;
                } else {
                    //else continue and print the longestConsecutive
                    Set<Integer> sequence = longestSequence.longestConsecutive(list);
                    output.println("Longest Sequence: " + sequence + " with Length: " + sequence.size());
                    output.flush();
                }
            }
            //close connection when leaving loop (due to try-with-resources this cannot be completed in the finally block)
            closeConnection(serverSocket, socket, output, input);
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    //helper function
    private static void closeConnection(ServerSocket serverSocket, Socket socket, PrintWriter os, BufferedReader is) throws IOException {
        os.close();
        is.close();
        socket.close();
        serverSocket.close();
        System.out.println("Server closing.");
    }
}
