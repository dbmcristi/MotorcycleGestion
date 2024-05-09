module myproject.ui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    requires myproject.domain;
//    requires myproject.servicemodule;
    requires java.sql;
    requires myproject.networking;
    requires HibernateModule;

    opens com.example.subarubaja to javafx.fxml;
    exports com.example.subarubaja;
}