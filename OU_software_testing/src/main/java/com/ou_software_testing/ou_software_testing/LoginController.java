package com.ou_software_testing.ou_software_testing;

import com.ou_software_testing.ou_software_testing.pojo.User;
import com.ou_software_testing.ou_software_testing.services.JdbcServices;
import com.ou_software_testing.ou_software_testing.services.UserServices;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable{
    
    @FXML private PasswordField txt_password;
    @FXML private TextField txt_phone_email, txt_name, txt_phone, txt_location, txt_email;
    @FXML private Button btn_login;
    @FXML private ComboBox<String> cb_sex;
    
    private User user;
    private Stage stage;
    private Scene scene;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    private void switchToMain(ActionEvent actionEvent, String role) throws IOException {
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
        MainController mainController = loader.getController();
        mainController.setUser(user);
        
        stage.setScene(scene);
        stage.hide();
        stage.show();
    }
    
    @FXML
    private void loadgenderList() {
        List<String> sexList = new ArrayList<>();
        sexList.add("male");
        sexList.add("female");
        cb_sex.getItems().clear();
        ObservableList obList = FXCollections.observableList(sexList);
        cb_sex.setItems(obList);
    }
    
    @FXML 
    private void switchToRegister(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(App.loadFXML("register"));
        stage.setScene(scene);
        stage.hide();
        stage.show();
    }
    
    @FXML
    private void getLoginInfo(ActionEvent actionEvent) {
        if(txt_password.getText().length() != 0 && txt_phone_email.getText().length() != 0) {       
              try {
                    Connection conn = JdbcServices.getConnection();
                    UserServices userServices = new UserServices(conn);

                    user = userServices.getUserInfo(txt_password.getText(), txt_phone_email.getText());
                    if(user != null) {
                        switchToMain(actionEvent, user.getRole());
                    } else {
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setHeaderText("Sai thông tin");
                        a.setTitle("Dialog");
                        a.setContentText("Sai mật khẩu hoặc tài khoản người dùng");
                        a.show();
                    }


                } catch (Exception ex) {
                    System.err.println(ex);
                }
        }
    }
    @FXML 
    private void registerLoginInfo(ActionEvent actionEvent) {
        String name = txt_name.getText();
        String password = txt_password.getText();
        String email =  txt_email.getText();
        String location = txt_location.getText();
        String phone = txt_phone.getText();
        String sex = cb_sex.getValue();
        if(txt_password.getText().length() != 0 
                && txt_phone.getText().length() != 0
                && txt_email.getText().length() != 0
                && txt_name.getText().length() != 0
                && txt_location.getText().length() != 0) {       
              
              try {
                    Connection conn = JdbcServices.getConnection();
                    UserServices userServices = new UserServices(conn);
                    
                    boolean kq = userServices.addUserInfo(name,sex,location,phone,email,password);
                    if(kq) {
                        user = userServices.getUserInfo(phone, password);
                        switchToMain(actionEvent, user.getRole());
                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.setHeaderText("Đăng ký thành công");
                        a.setTitle("Success");
                        a.setContentText("Thành công");
                        a.show();
                    } else {
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setHeaderText("Sai thông tin");
                        a.setTitle("Dialog");
                        a.setContentText("Sai thông tin người dùng, vui lòng nhập đầy đủ");
                        a.show();
                    }


                } catch (Exception ex) {
                    System.err.println(ex);
                }
        }
    }

    
    
}
