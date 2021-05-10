module com.ou_software_testing.ou_software_testing {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires momopayment;
//    requires gson;
    requires com.google.zxing;
    requires com.google.zxing.javase;
    requires log4j;
    
    opens com.ou_software_testing.ou_software_testing to javafx.fxml;
    exports com.ou_software_testing.ou_software_testing;
}