module com.example.dqlite2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens com.example.dqlite2 to javafx.fxml;
    exports com.example.dqlite2;
    exports com.company;
    opens com.company to javafx.fxml;
}