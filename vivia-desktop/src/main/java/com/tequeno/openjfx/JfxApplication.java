package com.tequeno.openjfx;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * VM args: --module-path=E:\plugin\javafx-sdk-17.0.12\lib --add-modules=javafx.controls
 */
public class JfxApplication extends Application {

    @Override
    public void init() throws Exception {
        System.out.println("init");
    }

    @Override
    public void stop() throws Exception {
        System.out.println("stop");
    }

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("start");
        stage.setTitle("JfxApplication");

        stage.show();
    }

    public static void main(String[] args) {
        launch(JfxApplication.class, args);
    }
}
