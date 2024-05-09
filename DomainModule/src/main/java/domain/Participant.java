package domain;

import java.io.Serializable;

public class Participant extends Entity<Integer>{

    String teamName;
    String name;
    int idRace;

//    public Participant(Integer integer, String teamName, String name, int idRace) {
//        super(integer);
//        this.teamName = teamName;
//        this.name = name;
//        this.idRace = idRace;
//    }
public Participant(Integer integer, String teamName, String name, int idRace) {
    super(integer);
    this.teamName = teamName;
    this.name = name;
    this.idRace = idRace;
}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getIdRace() {
        return idRace;
    }

    public void setIdRace(int idRace) {
        this.idRace = idRace;
    }


    @Override
    public String toString() {
        return "Participant{" +
                "id=" + id + '\'' +
                "name='" + name + '\'' +
                ", teamName='" + teamName + '\'' +
                ", idRace=" + idRace +
                '}';
    }
}
