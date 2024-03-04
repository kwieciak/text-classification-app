module org.example.project1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens org.example.project1 to javafx.fxml;
    exports org.example.project1;
}