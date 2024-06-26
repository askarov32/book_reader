module com.example.bookreader {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires org.apache.pdfbox;
    requires java.desktop;
    requires javafx.swing;
    requires javafx.media;

    opens com.example.bookreader to javafx.fxml;
    exports com.example.bookreader;
}