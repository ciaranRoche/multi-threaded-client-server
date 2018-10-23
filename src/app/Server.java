package app;

import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class Server extends JFrame{
    // Text area for displaying contents
    private JTextArea jta = new JTextArea();

    private JButton exit_btn = new JButton("Exit");

    public static void main(String[] args) {
        new Server();
    }

    public Server() {
        // Place text area on the frame
        setLayout(new BorderLayout());
        add(new JScrollPane(jta), BorderLayout.CENTER);
        add(exit_btn, BorderLayout.EAST);

        setTitle("Server");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true); // It is necessary to show the frame here!

        exit_btn.addActionListener(e -> System.exit(0));

        try {
            // Create a server socket
            ServerSocket serverSocket = new ServerSocket(8000);
            jta.append("Server started at " + new Date() + '\n');

            while(true){
                Socket socket = serverSocket.accept();
                Thread thread = new ServerThread(socket);
                thread.start();
                }
            } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ServerThread extends Thread{

    public Socket socket;

    public ServerThread(Socket socket){
        this.socket = socket;
    }

    public void run(){
        // Create data input and output streams
        try {
            DataInputStream inputFromClient = new DataInputStream(
                    socket.getInputStream());
        DataOutputStream outputToClient = new DataOutputStream(
                socket.getOutputStream());

            while (true) {
                // Receive radius from the client
                double radius = inputFromClient.readDouble();

                // Compute area
                double area = radius * radius * Math.PI;

                // Send area back to the client
                outputToClient.writeDouble(area);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}