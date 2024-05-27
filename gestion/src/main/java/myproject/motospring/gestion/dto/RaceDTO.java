package myproject.motospring.gestion.dto;

import jakarta.persistence.Column;
import org.springframework.stereotype.Component;

@Component
public class RaceDTO {
    private  Integer id;
    private  String name;
    private  String engineCapacity;

    public RaceDTO() {
    }

    public RaceDTO(Integer id, String name, String engineCapacity) {
        this.id = id;
        this.name = name;
        this.engineCapacity = engineCapacity;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEngineCapacity() {
        return engineCapacity;
    }

    @Override
    public String toString() {
        return "RaceDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", engineCapacity='" + engineCapacity + '\'' +
                '}';
    }
}
