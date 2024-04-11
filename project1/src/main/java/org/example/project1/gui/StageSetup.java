package org.example.project1.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.IOException;


public class StageSetup {
    @Getter
    private static Stage stage;

    private static void setStage(Stage stage) {
        StageSetup.stage = stage;
    }

    private static Parent loadFxml(String fxml) {
        try {
            return new FXMLLoader(StageSetup.class.getResource(fxml)).load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void buildStage(String filePath) {
        stage.setScene(new Scene(loadFxml(filePath)));
        stage.sizeToScene();
        stage.show();
    }

    public static void buildStage(Stage stage, String filePath)  {
        setStage(stage);
        stage.setScene(new Scene(loadFxml("/org/example/project1/" + filePath)));
        stage.sizeToScene();
        stage.show();
    }

}