package com.ou_software_testing.ou_software_testing;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainController {
    
    private Stage stage;
    private Scene scene;
    private String[] name = {
        "sell_menu", 
        "statistic_menu", 
        "products_menu", 
        "search_menu", 
        "login",
        "main"};
    
    @FXML
    private void switchToSellMenu(ActionEvent actionEvent) {
        switchMenu(actionEvent, name[0]);
    }
    @FXML
    private void switchToStatisticMenu(ActionEvent actionEvent) {
        switchMenu(actionEvent, name[1]);
    }
    @FXML
    private void switchToProductsMenu(ActionEvent actionEvent) {
        switchMenu(actionEvent, name[2]);
    }
    @FXML
    private void switchToSearchMenu(ActionEvent actionEvent) {
        switchMenu(actionEvent, name[3]);
    }
    @FXML
    private void switchToLogin(ActionEvent actionEvent){
        switchMenu(actionEvent, name[4]);
    }
    @FXML
    private void switchMain(ActionEvent actionEvent) {
        switchMenu(actionEvent, name[5]);
    }
    
    private void switchMenu(ActionEvent actionEvent, String name) {
        try {
            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(App.loadFXML(name));
            stage.setScene(scene);
            stage.hide();
            stage.show();
        } catch (IOException ex) {
            System.out.println("Error while switching to" + name);
            ex.printStackTrace();
        }
    }
}