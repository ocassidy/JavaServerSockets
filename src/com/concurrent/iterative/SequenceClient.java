package com.concurrent.iterative;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class SequenceClient {
    public static void main(String[] args) throws IOException {
        //instantiate socket and input/output streams
        Socket socket = null;
        PrintWriter outputStream = null;
        BufferedReader inputStream = null;
        BufferedReader userInputStream = new BufferedReader(new InputStreamReader(System.in));
        try {
            //try assigning the socket port to localhost 4000 to establish connection
            socket = new Socket("localhost", 4000);
            outputStream = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Cannot connect to localhost");
        } catch (IOException e) {
            System.err.println("Couldn't get I/O streams");
        }

        // if successfully connected to localhost port and socket
        if (socket != null && outputStream != null && inputStream != null) {
            try {
                String userInput;
                //create sentinel value for while loop
                String sentinelExit = "exit";
                //print instructions to user
                System.out.println("Connected to localhost on port 4000.");
                System.out.println("Enter \"finish\" to print the results.");
                System.out.println("Enter \"exit\" to exit the program.");
                System.out.println("Enter a number to continue.");
                //enter while loop
                // exit close connection if sentinel value "exit" is used
                while (!(userInput = userInputStream.readLine().toLowerCase()).equals(sentinelExit)) {
                    //print initial input
                    outputStream.println(userInput);
                    outputStream.flush();

                    // finish the input and close connection if "finish" is entered
                    if (userInput.equalsIgnoreCase("finish")) {
                        System.out.println("The longest sequence: " + inputStream.readLine().toLowerCase() + ".");
                        System.out.println("Sequence printed closing connection.");
                        //break out of if and while
                        break;
                    } else {
                        //else continue until "finish" is entered
                        System.out.println("Your entry is " + inputStream.readLine().toLowerCase() + ".");
                        System.out.println("Enter \"finish\" to print the results.");
                        System.out.println("Enter \"exit\" to exit the program.");
                    }
                }
            } catch (IOException e) {
                System.err.println("I/O failed to communicate");
            } finally {
                //socket closes when loop is finished
                closeConnection(socket, outputStream, inputStream);
            }
        }
    }

    //helper function
    private static void closeConnection(Socket socket, PrintWriter os, BufferedReader is) throws IOException {
        os.close();
        is.close();
        socket.close();
        System.out.println("Connection closed.");
    }
}