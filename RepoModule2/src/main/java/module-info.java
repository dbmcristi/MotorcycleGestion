module myproject.repository {
   requires myproject.jdbcutils;
   requires myproject.domain;
    requires java.sql;

    exports repository;
}