package app;

import javax.swing.*;
import java.awt.*;

public class App extends JFrame {

    private JButton btn_client = new JButton("Click for Client");

    public App(){
        setLayout(new BorderLayout());
        add(btn_client, BorderLayout.CENTER);

        setTitle("SOCKETnTREADS APP");
        setSize(500,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        btn_client.addActionListener(e -> new Client());

    }

    public static void main(String[] args){
        new App();
        new Server();
    }
}
