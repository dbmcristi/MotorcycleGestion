package repository;

import domain.Entity;
import domain.Participant;

import java.util.List;

public interface  IParticipantRepo<T extends Entity<ID>, ID> extends IRepo<T,ID> {
    Integer getParticipantSizeByRace(int raceId) throws Exception ;

    List<Participant> getParticipantByTeamName(String teamName) throws Exception;

    Integer showParticipNrByRaceName(String raceName) throws Exception;
}
