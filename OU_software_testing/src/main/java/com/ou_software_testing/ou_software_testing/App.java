package com.ou_software_testing.ou_software_testing;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    /**
     * @return the stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * @param stage the stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    private static Scene scene;
    private Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        this.setStage(stage);
        scene = new Scene(loadFXML("login"));        
        stage.setTitle("Sale app");
        this.getStage().setScene(scene);
        this.getStage().setResizable(true);
        this.getStage().hide();
        this.getStage().show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
    
    public static void setRoot(String fxml, String title) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        System.out.print("loadFXML running");
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }
}