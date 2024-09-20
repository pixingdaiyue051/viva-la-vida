package com.tequeno.swing;

import javax.swing.*;
import java.awt.*;

public class SwingApplication {

    public void run() {

        JButton jButton = new JButton();
        jButton.setText("btn10");
        jButton.addActionListener(e -> System.out.println(jButton.getAccessibleContext()));

        JFrame jFrame = new JFrame();
        jFrame.setLayout(new FlowLayout());

        jFrame.setSize(500, 500);
        jFrame.setLocation(500, 500);

        jFrame.add(jButton);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void main(String[] args) {
        SwingApplication application = new SwingApplication();
        application.run();
    }
}
