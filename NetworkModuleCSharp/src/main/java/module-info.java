module myproject.networkingcsharp {
    requires myproject.servicemodule;
    requires myproject.domain;
    requires myproject.repository;
    requires org.apache.commons.lang3;

    exports networkcsharp;
}