package com.ou_software_testing.ou_software_testing.controller;

import com.ou_software_testing.ou_software_testing.GlobalContext;
import com.ou_software_testing.ou_software_testing.pojo.User;
import com.ou_software_testing.ou_software_testing.services.JdbcServices;
import com.ou_software_testing.ou_software_testing.services.UserServices;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController extends Controller{
    
    @FXML private PasswordField txt_password;
    @FXML private TextField txt_phone_email;
    @FXML private Button btn_login;
    
    private User user;
    private String info, pw;
    
    @FXML
    private void getLoginInfo(ActionEvent actionEvent) {
        if(txt_password.getText().length() != 0 && txt_phone_email.getText().length() != 0) {       
              pw = txt_password.getText();
              info = txt_phone_email.getText();
              try {
                    Connection conn = JdbcServices.getConnection();
                    UserServices userServices = new UserServices(conn);
                    
                    user = userServices.getUserInfo(info, pw);
                    if(user != null) {
                        GlobalContext.setUser(user);
                        this.switchToMain(actionEvent);
                    } else {
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setHeaderText("Sai thông tin");
                        a.setTitle("Dialog");
                        a.setContentText("Sai mật khẩu hoặc tài khoản người dùng");
                        a.show();
                    }
                } catch (IOException | SQLException ex) {
                    System.err.println(ex);
                }
        }
    }
}
