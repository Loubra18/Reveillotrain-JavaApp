module be.bralion.appreveillotrain {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.net.http;
    requires com.fazecast.jSerialComm;

    opens be.bralion.appreveillotrain to javafx.fxml;
    //exports be.bralion.appreveillotrain;
    exports be.bralion.appreveillotrain.controller;
    opens be.bralion.appreveillotrain.controller to javafx.fxml;
    exports be.bralion.appreveillotrain.view;
    opens be.bralion.appreveillotrain.view to javafx.fxml;
}