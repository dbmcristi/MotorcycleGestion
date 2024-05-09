package repository;

import domain.Entity;
import domain.Race;

import java.util.List;

public interface IRaceRepo<T extends Entity<ID>, ID> extends IRepo<T,ID> {

    List<Race> getRaceByEngineSize(String engSize) throws RepositoryException;
     boolean isRacePresent(Integer raceId) throws RepositoryException;

}
