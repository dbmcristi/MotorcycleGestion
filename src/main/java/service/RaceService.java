package service;

import domain.EngineCapacity;
import domain.Entity;
import domain.Race;
import repository.RaceRepo;
import repository.RepositoryException;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class  RaceService {
    RaceRepo raceRepo;

    public RaceService(RaceRepo raceRepo) {
        this.raceRepo = raceRepo;
    }
    public List<Race> showRacesByEngineSize(String engineCapacity) throws RepositoryException {
        return raceRepo.getRaceByEngineSize(engineCapacity);
    }

    public void add(Race race3) throws RepositoryException {
        raceRepo.add(race3);
    }

    public List<Race> getAll()  {
        try {
            return raceRepo.getAll();
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }
    }
    public int getLastId() throws RepositoryException {
        var races = raceRepo.getAll().stream()
                .sorted(Comparator.comparingInt(Entity::getId))
                .collect(Collectors.toList());
        Collections.reverse(races);
        if (races.isEmpty())
            return 0;
        return races.get(0).getId();
    }
}
