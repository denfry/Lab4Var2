module com.example.lab4var2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.lab4var2 to javafx.fxml;
    exports com.example.lab4var2;
}