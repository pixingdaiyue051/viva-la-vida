package com.tequeno.openjfx;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainStage extends Application {

    private final static Logger log = LoggerFactory.getLogger(MainStage.class);

    @Override
    public void init() throws Exception {
        log.info("init");
    }

    @Override
    public void stop() throws Exception {
        log.info("stop");
    }

    @Override
    public void start(Stage stage) throws Exception {
        log.info("start");

//        try (FileInputStream fis = new FileInputStream("/data/pic/icon-moon.png")) {
//            stage.getIcons().add(new Image(fis));
//        } catch (Exception e) {
//            log.error("设置图标失败", e);
//        }

        stage.getIcons().add(new Image("assets/icon-moon.png"));
//        stage.setOpacity(0.65d);
//        stage.setWidth(500.0d);
//        stage.setHeight(500.0d);
//        stage.setX(500.0d);
//        stage.setY(500.0d);

//        stage.initStyle(StageStyle.UNDECORATED);
//        stage.setAlwaysOnTop(true);

//        stage.widthProperty().addListener((observable, oldValue, newValue) -> log.info("oldWidth[{}],newWidth[{}]", oldValue, newValue));
//        stage.heightProperty().addListener((observable, oldValue, newValue) -> log.info("oldHeight[{}],newHeight[{}]", oldValue, newValue));

        stage.setTitle("JfxApplication");
        stage.show();

        Stage s1 = new Stage();
        s1.setTitle("s1");
        s1.initModality(Modality.APPLICATION_MODAL);

        Stage s2 = new Stage();
        s2.setTitle("s2");

        Stage s3 = new Stage();
        s3.setTitle("s3");


        s1.show();
        s2.show();
        s3.show();
    }
}
