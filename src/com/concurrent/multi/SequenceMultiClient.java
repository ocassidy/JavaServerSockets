package com.concurrent.multi;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class SequenceMultiClient {
    public static void main(String[] args) throws IOException {
        //instantiate socket and input/output streams
        Socket socket = null;
        PrintWriter outputStream = null;
        BufferedReader inputStream = null;
        BufferedReader userInputStream = new BufferedReader(new InputStreamReader(System.in));
        try {
            //try assigning the socket port to localhost 4444 to establish connection
            socket = new Socket("localhost", 4444);
            outputStream = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Cannot connect to localhost");
        } catch (IOException e) {
            System.err.println("Couldn't get I/O streams");
        }

        // if successfully connected to localhost port and socket
        if (socket != null && outputStream != null && inputStream != null) {
            //print socket port started on for client
            System.out.println("Connected to localhost multiserver on port: " + socket.getLocalPort());
            String threadName = inputStream.readLine().toLowerCase();
            //print thread that socket is connected to
            System.out.println("Your connection is running in thread: " + threadName + ".");
            try {
                String userInput;
                //create sentinel value for while loop
                String sentinelExit = "exit";
                //print instructions to user
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
                closeConnection(threadName, socket, outputStream, inputStream);
            }
        }
    }

    //helper function
    private static void closeConnection(String threadName, Socket socket, PrintWriter output, BufferedReader input) throws IOException {
        output.close();
        input.close();
        socket.close();
        System.out.println("Connection closed for " + threadName);
    }
}