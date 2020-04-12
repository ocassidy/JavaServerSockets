package com.concurrent.multi;

import com.concurrent.shared.LongestSequence;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class SequenceMultiServerThread extends Thread {

    private Socket socket;

    SequenceMultiServerThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        //instantiate class used to do sequence calculations
        LongestSequence longestSequence = new LongestSequence();

        //try with resources on creating streams with the constructor injected socket
        try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()))) {
            System.out.println("Received new connection.");
            System.out.println("New thread: " + Thread.currentThread().getName() + " started.");
            output.println(Thread.currentThread().getName());
            output.flush();
            while (true) {
                //instantiate list with user values added on every input
                List<Integer> list = longestSequence.getInput(input, output);

                //if list is empty return text and close connection
                if (list.isEmpty()) {
                    output.println("List from " + Thread.currentThread().getName() + " was empty, closing connection.");
                    output.flush();
                    break;
                } else {
                    //else continue and print the longestConsecutive
                    output.println(longestSequence.longestConsecutive(list, output));
                    output.flush();
                }
            }
            //close connection when leaving loop (due to try-with-resources this cannot be completed in the finally block)
            closeConnection(socket, output, input);
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    //helper function
    private static void closeConnection(Socket socket, PrintWriter output, BufferedReader input) throws IOException {
        System.out.println(Thread.currentThread().getName() + " closing connection.");
        output.close();
        input.close();
        socket.close();
    }
}

