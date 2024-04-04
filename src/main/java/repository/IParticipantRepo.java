package repository;

import domain.Entity;
import domain.Participant;

import java.util.List;

public interface  IParticipantRepo<T extends Entity<ID>, ID> extends IRepo<T,ID> {
    Integer getParticipantSizeByRace(int raceId) throws RepositoryException ;

    List<Participant> getParticipantByTeamName(String teamName) throws RepositoryException;

    Integer showParticipNrByRaceName(String raceName) throws RepositoryException;
}
