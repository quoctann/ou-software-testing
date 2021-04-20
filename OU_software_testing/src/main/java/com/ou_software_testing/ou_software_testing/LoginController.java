package com.ou_software_testing.ou_software_testing;

import com.ou_software_testing.ou_software_testing.pojo.User;
import com.ou_software_testing.ou_software_testing.services.JdbcServices;
import com.ou_software_testing.ou_software_testing.services.UserServices;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController extends Controller{
    
    @FXML private PasswordField txt_password;
    @FXML private TextField txt_phone_email;
    @FXML private Button btn_login;
    
//    private User user;
    private String info, pw;
//    private Stage stage;
//    private Scene scene;
    
//    private void switchToMain(ActionEvent actionEvent, String role) throws IOException {
//        Parent parent;
//        FXMLLoader loader = null;
//        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
//        //kiểm tra role
//        if("user".equals(role)) {
//            loader = new FXMLLoader(App.class.getResource("main_user.fxml"));
//            parent = loader.load();
//        } else {
//            loader = new FXMLLoader(App.class.getResource("main.fxml"));
//            parent = loader.load();
//        }
//        scene = new Scene(parent);
//        MainController mainController = loader.getController();
//        mainController.setUser(user);
//        
//        stage.setScene(scene);
//        stage.hide();
//        stage.show();
//    }
    
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

    @Override
    protected void setUser(User user) {}
}
