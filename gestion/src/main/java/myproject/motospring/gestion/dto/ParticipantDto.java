package myproject.motospring.gestion.dto;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import myproject.motospring.gestion.domain.Race;
import org.springframework.stereotype.Component;

@Component
public class ParticipantDto {
    private Integer id;
    private String teamName;
    private String name;
    private Integer idRace;

    public ParticipantDto() {
    }

    public ParticipantDto(Integer id, String teamName, String name, Integer idRace) {
        this.id = id;
        this.teamName = teamName;
        this.name = name;
        this.idRace = idRace;
    }

    public Integer getId() {
        return id;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getName() {
        return name;
    }

    public Integer getIdRace() {
        return idRace;
    }

    @Override
    public String toString() {
        return "ParticipantDto{" +
                "id=" + id +
                ", teamName='" + teamName + '\'' +
                ", name='" + name + '\'' +
                ", idRace=" + idRace +
                '}';
    }
}
