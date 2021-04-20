package com.ou_software_testing.ou_software_testing.controller;

import com.ou_software_testing.ou_software_testing.GlobalContext;
import com.ou_software_testing.ou_software_testing.App;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class Controller {
    @FXML protected Text txt_name;
    
    @FXML
    protected void switchToMain(ActionEvent actionEvent) throws IOException {
        String role = GlobalContext.getUser().getRole();

        //kiểm tra role
        if("user".equals(role)) {
            App.setRoot("main_user");
        } else {
            App.setRoot("main");
        }
    }
}
