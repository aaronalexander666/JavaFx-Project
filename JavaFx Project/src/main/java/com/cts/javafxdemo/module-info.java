module com.cts.javafxdemo {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires transitive java.sql;

    opens com.cts.javafxdemo to javafx.fxml;
    exports com.cts.javafxdemo;
}
