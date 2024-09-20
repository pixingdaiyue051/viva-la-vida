package com.tequeno.awt;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseAdapterSolver extends MouseAdapter {

    @Override
    public void mouseClicked(MouseEvent e) {
        int clickCount = e.getClickCount();
        if (2 == clickCount) {
            System.out.println("双击");
        } else if(1 == clickCount) {
            System.out.println("单击");
        } else {
            System.out.println("点击鼠标");
        }
        int button = e.getButton();
        if(1 == button) {
            System.out.println("左键");
        } else if( 3 == button) {
            System.out.println("右键");
        } else{
            System.out.println("滚轮");
        }
    }


}