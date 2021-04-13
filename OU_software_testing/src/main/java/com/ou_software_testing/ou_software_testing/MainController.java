package com.ou_software_testing.ou_software_testing;

import java.io.IOException;
import javafx.fxml.FXML;

public class MainController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("login");
    }
}