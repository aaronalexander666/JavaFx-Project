module com.cts.javafxdemo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.cts.javafxdemo to javafx.fxml;
    exports com.cts.javafxdemo;
}
