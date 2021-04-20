package com.ou_software_testing.ou_software_testing.controller;

import com.ou_software_testing.ou_software_testing.DataTemporary;
import com.ou_software_testing.ou_software_testing.App;
import com.ou_software_testing.ou_software_testing.pojo.ListProduct;
import com.ou_software_testing.ou_software_testing.pojo.Product;
import com.ou_software_testing.ou_software_testing.services.JdbcServices;
import com.ou_software_testing.ou_software_testing.services.ProductServices;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class SellMenuController extends Controller implements Initializable{
    @FXML private TextField txt_pid;
    @FXML private TextField txt_quantity;
    @FXML private Button btn_add_adjust;
    @FXML private Button btn_abort;
    @FXML private Button btn_confirm_order;
    @FXML private Text txt_sum;
    @FXML private TableView<Product> tbProductSelection;
    
    private ListProduct listProduct = new ListProduct();
    private String pid, quantity;

    @FXML 
    private void HandleConfirmOrder(ActionEvent actionEvent) throws IOException{
        DataTemporary.setListProductSelection(listProduct);
        App.setRoot("notify_success_menu");
    }
    
    @FXML 
    private void HandleAbort(ActionEvent actionEvent) throws IOException{
        this.switchToMain(actionEvent);
    }
    
    @FXML
    private void HandleAdd(ActionEvent actionEvent) {
        pid = txt_pid.getText();
        quantity = txt_quantity.getText();
        
        try {
            Connection conn = JdbcServices.getConnection();
            ProductServices productServices = new ProductServices(conn);

            Product product = productServices.getProductById(Integer.parseInt(pid));
            if (product == null) return;
            
            product.setCount(Integer.parseInt(txt_quantity.getText()));
            
            listProduct.addProduct(product);
            loadProducts();
            
            txt_sum.setText(listProduct.getTotalPrice().toString());
            txt_quantity.setText("1");
            txt_pid.setText("");
        } catch (NumberFormatException | SQLException ex) {
            System.err.println(ex);
        }
    }
    
    private void loadColumns() {
        TableColumn colId = new TableColumn("PID");
        colId.setCellValueFactory(new PropertyValueFactory("id"));

        TableColumn colName = new TableColumn("Name");
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        
        TableColumn colQuantity = new TableColumn("Quantity");
        colQuantity.setCellValueFactory(new PropertyValueFactory("count"));
        
        TableColumn colPrice = new TableColumn("Price");
        colPrice.setCellValueFactory(new PropertyValueFactory("price"));
        
        this.tbProductSelection.getColumns().addAll(colId, colName, colQuantity, colPrice);
    }
    
    private void loadProducts(){
        tbProductSelection.setItems(FXCollections.observableArrayList(this.listProduct.getListProduct()));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadColumns();
        loadProducts();
        
        tbProductSelection.setOnMouseClicked(event -> {
            Product p = tbProductSelection.getSelectionModel().getSelectedItem();

            txt_quantity.setText(String.valueOf(p.getCount()));
            txt_pid.setText(String.valueOf(p.getId()));
        });
    }
}
