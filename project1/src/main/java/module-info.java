module org.example.project1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.xml;
    requires org.apache.commons.io;

    opens org.example.project1 to javafx.fxml;
    exports org.example.project1;
    exports org.example.project1.gui;
    opens org.example.project1.gui to javafx.fxml;
}