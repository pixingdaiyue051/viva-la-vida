package com.tequeno.awt;

import java.awt.*;

public class AwtApplication {

    public void run() {


        Button button = new Button();
        button.setLabel("testing");
        button.setBounds(0, 0, 10, 10);
        button.addActionListener(e -> System.out.println(e.getActionCommand()));

        TextField textField = new TextField();
        textField.setText("testing");
        textField.setBounds(10, 0, 20, 20);


        Frame f = new Frame();
        f.setTitle("测试");
        f.setLayout(new FlowLayout());
        f.setVisible(true);
        f.setSize(500, 500);
        f.setLocation(500, 500);
        f.addWindowListener(new WindowAdapterSolver(f));
        f.addMouseListener(new MouseAdapterSolver());
        f.addKeyListener(new KeyAdapterSolver());

        f.add(button);
        f.add(textField);

    }

    public static void main(String[] args) {
        AwtApplication application = new AwtApplication();
        application.run();
    }
}
