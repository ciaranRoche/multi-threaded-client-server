package utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerThread extends Thread{

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
