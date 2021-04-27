module com.ou_software_testing.ou_software_testing {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome;
    opens com.ou_software_testing.ou_software_testing to javafx.fxml;
    exports com.ou_software_testing.ou_software_testing;
}