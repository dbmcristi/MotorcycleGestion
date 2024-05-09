package myproject.hibernate.domain;

import jakarta.persistence.Entity;

@Entity
public enum EngineCapacity {


    CM_125(125),
    CM_250(250),
    CM_300(300),
    CM_400(400),
    CM_500(500),
    CM_600(600),
    CM_700(700),
    CM_1000(1000),
    CM_1250(1250),
    ;
    private int number;
    EngineCapacity(int number) {
        this.number = number;
    }

    EngineCapacity() {

    }

    public int getNumber() {
        return number;
    }
}
