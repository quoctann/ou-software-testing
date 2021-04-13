module com.ou_software_testing.ou_software_testing {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.ou_software_testing.ou_software_testing to javafx.fxml;
    exports com.ou_software_testing.ou_software_testing;
}