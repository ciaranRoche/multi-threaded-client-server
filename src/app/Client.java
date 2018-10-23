package app;

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

    // Text area to display contents
    private JTextArea jta = new JTextArea();

    private JButton btn_exit = new JButton("EXIT");
    private JButton btn_send = new JButton("SEND");

    // IO streams
    private DataOutputStream toServer;
    private DataInputStream fromServer;

    public Client() {
        // Panel p to hold the label and text field
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.add(new JLabel("Enter radius"), BorderLayout.WEST);
        p.add(jtf, BorderLayout.CENTER);
        p.add(btn_send, BorderLayout.WEST);
        p.add(btn_exit, BorderLayout.EAST);

        jtf.setHorizontalAlignment(JTextField.RIGHT);

        setLayout(new BorderLayout());
        add(p, BorderLayout.NORTH);
        add(new JScrollPane(jta), BorderLayout.CENTER);

        jtf.addActionListener(new Listener()); // Register listener

        btn_send.addActionListener(new Listener());
        btn_exit.addActionListener(e -> System.exit(0));

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