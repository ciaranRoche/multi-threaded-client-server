package models;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


/*
AUTHOR : Ciaran Roche
DATE : 24/OCT/2018

ABOUT : Class Client extends JFrame,
        Creates socket to Server
        Allows for input of student id
        Accepts response from Server
 */
public class Client extends JFrame {
    // Text field for receiving radius
    private JTextField jtf = new JTextField();

    // Label
    JLabel textLabel = new JLabel("Enter Student Number ->");

    // New Split plane for input
    private JSplitPane sp;

    // Text area to display contents
    private JTextArea jta = new JTextArea();

    // Buttons for send and exit
    private JButton btn_exit = new JButton("CLOSE CLIENT");
    private JButton btn_send = new JButton("SEND");

    // IO streams
    private DataOutputStream toServer;
    private DataInputStream fromServer;

    // Client Constructor
    public Client() {
        // Split panel sp to hold Label and text input
        sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, textLabel, jtf);

        // Panel p to hold the label and text field and split panel
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.add(new JLabel("Enter radius"), BorderLayout.WEST);
        p.add(sp, BorderLayout.CENTER);
        p.add(btn_send, BorderLayout.EAST);
        p.add(btn_exit, BorderLayout.WEST);

        jtf.setHorizontalAlignment(JTextField.RIGHT);

        setLayout(new BorderLayout());
        add(p, BorderLayout.NORTH);
        add(new JScrollPane(jta), BorderLayout.CENTER);

        jtf.addActionListener(new Listener()); // Register listener

        // Add action listener to buttons
        btn_send.addActionListener(new Listener());
        btn_exit.addActionListener(e -> dispose()); // Lamda close window

        setTitle("Client");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true); // It is necessary to show the frame here!

        try {
            // Create a socket to connect to the server
            Socket socket = new Socket("localhost", 8000);

            // Create an input stream to receive data from the server
            fromServer = new DataInputStream(socket.getInputStream());

            // Create an output stream to send data to the server
            toServer = new DataOutputStream(socket.getOutputStream());
        }
        catch (IOException ex) {
            jta.append(ex.toString() + '\n');
        }
    }

    // Listener class for client actions
    private class Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
            // Get the id from the text input
            String id = jtf.getText().trim();

            // Send the radius to the server
            toServer.writeUTF(id);
            toServer.flush();

            // Get response from server
            String response = fromServer.readUTF();

            // Display to the text area
            jta.append(response + "\n");

            }
            catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }
}