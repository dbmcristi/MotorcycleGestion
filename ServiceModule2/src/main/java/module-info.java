module myproject.servicemodule {
    requires java.sql;
    requires myproject.repository;
    requires myproject.domain;

    exports service;
}