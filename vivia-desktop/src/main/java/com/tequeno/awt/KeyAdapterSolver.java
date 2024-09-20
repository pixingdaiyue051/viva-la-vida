package com.tequeno.awt;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyAdapterSolver extends KeyAdapter {

    /**
     * 无法获取到keyCode
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("keyTyped");
        int keyCode = e.getKeyCode();
        System.out.println(keyCode);
        char keyChar = e.getKeyChar();
        System.out.println(keyChar);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("keyPressed");
        int keyCode = e.getKeyCode();
        System.out.println(keyCode);
        char keyChar = e.getKeyChar();
        System.out.println(keyChar);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("keyReleased");
        int keyCode = e.getKeyCode();
        System.out.println(keyCode);
        char keyChar = e.getKeyChar();
        System.out.println(keyChar);
    }
}
