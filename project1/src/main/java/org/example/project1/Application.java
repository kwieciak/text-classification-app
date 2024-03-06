package org.example.project1;

import javafx.stage.Stage;
import org.example.project1.gui.StageSetup;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) {
        StageSetup.buildStage(stage, "main-stage.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}