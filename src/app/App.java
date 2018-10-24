package app;

import models.Client;
import models.Server;

import javax.swing.*;
import java.awt.*;

/*
AUTHOR : Ciaran Roche
DATE : 24/OCT/2018

ABOUT : Class App extends JFrame,
        Launches Server
        Launches Jframe to allow user create multiple Clients
 */

public class App extends JFrame {

    // Button for client creation
    private JButton btn_client = new JButton("Click for Client");

    private App(){
        // Create frame
        setLayout(new BorderLayout());
        add(btn_client, BorderLayout.CENTER);

        setTitle("SOCKETnTREADS APP");
        setSize(500,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // Add action listener lambda for launching client
        btn_client.addActionListener(e -> new Client());

    }

    public static void main(String[] args){
        new App();
        new Server();
    }
}
