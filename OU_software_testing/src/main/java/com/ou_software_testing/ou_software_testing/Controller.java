/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ou_software_testing.ou_software_testing;

import com.ou_software_testing.ou_software_testing.pojo.User;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Admin
 */
public class Controller {
    @FXML protected Text txt_name;
    
    protected Stage stage;
    protected Scene scene;
    protected User user;
    
    @FXML
    protected void switchToMain(ActionEvent actionEvent) throws IOException {
        String role = user.getRole();
        Parent parent;
        FXMLLoader loader = null;
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        
        //kiểm tra role
        if("user".equals(role)) {
            loader = new FXMLLoader(App.class.getResource("main_user.fxml"));
            parent = loader.load();
        } else {
            loader = new FXMLLoader(App.class.getResource("main.fxml"));
            parent = loader.load();
        }
        scene = new Scene(parent);
        Controller controller = loader.getController();
        controller.setUser(user);
        
        stage.setScene(scene);
        stage.hide();
        stage.show();
    }
    
    protected void setUser(User user) {
        this.user = user;
        txt_name.setText(user.getRole() + ": " + user.getName());
    }
}
