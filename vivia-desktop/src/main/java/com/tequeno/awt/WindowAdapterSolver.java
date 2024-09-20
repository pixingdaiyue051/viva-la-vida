package com.tequeno.awt;


import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowAdapterSolver extends WindowAdapter {

    private final Frame frame;

    private Dialog dialog;

    public WindowAdapterSolver(Frame frame) {
        this.frame = frame;
    }

    /**
     * 右上角关闭按钮 默认无事件 改为点击弹窗提示
     *
     * @param e the event to be processed
     */
    @Override
    public void windowClosing(WindowEvent e) {
        if (null == dialog) {
            createDialog();
        }
        dialog.setVisible(true);
    }

    private void createDialog() {
        dialog = new Dialog(frame, "确认", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(100, 100);
        Point location = frame.getLocation();
        int width = frame.getWidth();
        int height = frame.getHeight();
        dialog.setLocation(new Point(location.x + width / 2, location.y + height / 2));

        Label label = new Label("R'U Sure？");
        Button cancelBtn = new Button("no");
        cancelBtn.addActionListener(e -> dialog.setVisible(false));
        Button okBtn = new Button("yes");
        okBtn.addActionListener(e -> System.exit(0));
        dialog.add(label, BorderLayout.NORTH);
        dialog.add(cancelBtn, BorderLayout.WEST);
        dialog.add(okBtn, BorderLayout.EAST);

        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dialog.setVisible(false);
            }
        });
    }
}
