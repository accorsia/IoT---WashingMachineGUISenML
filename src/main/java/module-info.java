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
    opens unimore.iot.resources;
    opens unimore.iot.model;
    exports unimore.iot;
    //exports unimore.iot.singleVersion;
    //opens unimore.iot.singleVersion to javafx.fxml;
    exports unimore.iot.gui;
    opens unimore.iot.gui to javafx.fxml;
    opens unimore.iot.utilities to com.google.gson;
}