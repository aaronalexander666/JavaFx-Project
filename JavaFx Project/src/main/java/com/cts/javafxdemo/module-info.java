module your.app.name {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; // Crucial for MySQL

    opens your.package.name to javafx.fxml;
    exports your.package.name;
}