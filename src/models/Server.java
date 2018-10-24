package models;

import utils.JDBCConnector;
import utils.ServerThread;
import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.*;
import java.awt.*;
import javax.swing.*;

/*
AUTHOR : Ciaran Roche
DATE : 24/OCT/2018

ABOUT : Class Server extends JFRAME,
        Checks for connection through JDBC
        Creates JFRAME for logging details,
        Creates Multi Threads for open sockets
 */

public class Server extends JFrame{
    // Text area for displaying contents
    private JTextArea jta = new JTextArea();

    private JButton exit_btn = new JButton("Exit App");

    private JDBCConnector conn = new JDBCConnector();

    public Server(){
        // Place text area on the frame
        setLayout(new BorderLayout());
        add(new JScrollPane(jta), BorderLayout.CENTER);
        add(exit_btn, BorderLayout.EAST);

        setTitle("Server");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true); // It is necessary to show the frame here!

        exit_btn.addActionListener(e -> System.exit(0));

        getConnection();

        try {
            // Create a server socket
            ServerSocket serverSocket = new ServerSocket(8000);
            jta.append("\nServer started at " + new Date() + '\n');
            // Create threads for incoming sockets
            while(true){
                Socket socket = serverSocket.accept();
                if (socket.isConnected()){
                    jta.append("Processing. . .\n");
                }
                Thread thread = new ServerThread(socket);
                thread.start();
                }
            } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getConnection(){
        try {
            conn.getConnection();
            jta.append("Successfully Connected to Database");
        } catch (SQLException e) {
            jta.append("Connection to Database Failed");
            e.printStackTrace();
        }
    }
}

