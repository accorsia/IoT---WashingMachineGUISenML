module unimore.iot {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
    requires californium.core;
    requires element.connector;
    requires com.google.gson;
    requires org.slf4j;

    opens unimore.iot to javafx.fxml;
    opens unimore.iot.request;
    opens unimore.iot.resources to com.google.gson;
    opens unimore.iot.model to com.google.gson;
    exports unimore.iot;
    //exports unimore.iot.singleVersion;
    //opens unimore.iot.singleVersion to javafx.fxml;
    exports unimore.iot.gui;
    opens unimore.iot.gui to javafx.fxml;
    opens unimore.iot.utilities to com.google.gson;
    opens unimore.iot.serialization to com.google.gson;
}