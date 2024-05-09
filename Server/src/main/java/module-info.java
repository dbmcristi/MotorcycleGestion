module myproject.server {
    requires myproject.networking;
//    requires myproject.servicemodule;
    requires myproject.domain;
//    requires org.apache.commons.lang3;
    requires HibernateModule;
    requires java.sql;
    exports socketServer;

}