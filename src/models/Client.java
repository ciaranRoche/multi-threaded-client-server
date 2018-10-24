package models;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

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
    private JButton btn_exit = new JButton("EXIT");
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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
            // Get the radius from the text field
            double radius = Double.parseDouble(jtf.getText().trim());

            // Send the radius to the server
            toServer.writeDouble(radius);
            toServer.flush();

            // Get area from the server
            double area = fromServer.readDouble();

            // Display to the text area
            jta.append("Radius is " + radius + "\n");
            jta.append("Area received from the server is "
              + area + '\n');

            }
            catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }
}