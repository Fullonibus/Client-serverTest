module com.example.nurabotaypls {
    requires javafx.controls;
    requires javafx.fxml;



    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    requires io.netty.buffer;
    requires io.netty.handler;
    requires io.netty.transport;
    requires io.netty.codec;
    requires io.netty.common;
    requires io.netty.resolver;
    requires javafx.base;


    opens com.example.nurabotaypls to javafx.fxml;
    exports com.example.nurabotaypls;

}