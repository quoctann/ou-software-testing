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

public class MainController extends Controller{
    
//    @FXML private Text txt_name;
    
    private Stage notifyStage;
    private Scene notifyScene;
//    private User user;
    private String[] name = {
        "sell_menu", 
        "statistic_menu", 
        "products_menu", 
        "search_menu", 
        "login",
        "main", 
        "main_user",
        "order_list", 
        "notify_success",
        "notify_fail"
    };
    
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
    @FXML
    private void switchMainUser(ActionEvent actionEvent) {
        switchMenu(actionEvent, name[6]);
    }
    @FXML
    private void switchOrderList(ActionEvent actionEvent) {
        switchMenu(actionEvent, name[7]);
    }
    @FXML
    private void switchNotifySucess(ActionEvent actionEvent) {
        switchMenu(actionEvent, name[8]);
    }
    @FXML
    private void switchNotifyFail(ActionEvent actionEvent) {
        switchMenu(actionEvent, name[9]);
    }
    
    private void switchMenu(ActionEvent actionEvent, String name) {
        try {
            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        
            FXMLLoader loader = new FXMLLoader(App.class.getResource(name + ".fxml"));
            scene = new Scene(loader.load());
            
            if (!"login".equals(name)){                
                Controller mainController = loader.getController();
                mainController.setUser(user);
            }
            
            stage.setScene(scene);
            stage.setTitle("Ứng dụng bán hàng");
            stage.hide();
            stage.show();
        } catch (IOException ex) {
            System.out.println("Error while switching to " + name);
            System.err.println(ex);
//            ex.printStackTrace();
        }
    }
    
    //need to check which user it is to switch to the correct menu
    @FXML 
    private void returnToWhichMainMenu() {
        
    }
    
    @FXML
    private void getNotifyMenu(boolean success) {
        notifyStage = new Stage();
        notifyStage.setAlwaysOnTop(true);
        notifyStage.setResizable(false);
        notifyStage.setTitle("Notify");
        
        try {
            if(success) {
                notifyScene = new Scene(App.loadFXML("notify_success_menu"));
            } else {
                notifyScene = new Scene(App.loadFXML("notify_fail_menu"));
            }
        } catch (IOException ex) {
            System.out.println("Error while switching to notify menu");
            ex.printStackTrace();
        } 
        
        notifyStage.setScene(notifyScene);
        notifyStage.show();
    }
    @FXML
    private void closeNotifyMenu() {
        notifyStage.hide();
    }
}