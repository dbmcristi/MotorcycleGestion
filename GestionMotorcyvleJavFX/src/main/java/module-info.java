module com.example.gestionmotorcyvlejavfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires lombok;
    requires org.slf4j;

    opens com.example.gestionmotorcyvlejavfx to javafx.fxml;
    exports com.example.gestionmotorcyvlejavfx;
    exports repository;
    exports service;
}