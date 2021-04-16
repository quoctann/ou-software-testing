package com.ou_software_testing.ou_software_testing;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginController {
    
    private Stage stage;
    private Scene scene;
    @FXML
    private void switchToMain(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(App.loadFXML("main"));
        stage.setScene(scene);
        stage.hide();
        stage.show();
    }
    
}
