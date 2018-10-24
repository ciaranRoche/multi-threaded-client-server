package utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
AUTHOR : Ciaran Roche
DATE : 24/OCT/2018

ABOUT : Class ServerThread extends Thread,
        Handles Sockets from Server class
        Checks DB via JDBC for Student id
        Return valid response for found Student id
        Returns response and closes socket for not found Student id
 */

public class ServerThread extends Thread{

    public Socket socket;

    private JDBCConnector conn = new JDBCConnector();

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
                // Receive id from client
                String studID = inputFromClient.readUTF();

                // Return result set from DB and send response to client
                ResultSet rs = conn.returnRecord(studID);
                if(rs.next()){
                    String fname = rs.getString("FNAME");
                    String sname = rs.getString("SNAME");
                    outputToClient.writeUTF("Welcome " + fname + " " + sname + "... You are now connected to the Server");
                } else {
                    outputToClient.writeUTF("Sorry " + studID + ". You are not a registered student. Bye");
                    socket.close(); // close socket
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
