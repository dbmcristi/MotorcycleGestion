package service;

import domain.EngineCapacity;
import domain.Entity;
import domain.Participant;
import repository.ParticipantRepo;
import repository.RaceRepo;
import repository.RepositoryException;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ParticipantService {
    ParticipantRepo participantRepo;
    RaceRepo raceRepo;

    public ParticipantService(ParticipantRepo participantRepo, RaceRepo raceRepo) {
        this.participantRepo = participantRepo;
        this.raceRepo = raceRepo;
    }

    public void addParticipant(Participant e) throws RepositoryException, SQLException {
        if (raceRepo.isRacePresent(e.getIdRace())) {
          participantRepo.add(e);
        } else {
           throw new RepositoryException("There is no such Race");
        }
    }
    public Integer showParticipNrByRaceName(String name) throws RepositoryException {
        return participantRepo.showParticipNrByRaceName(name);
    }
    public List<Participant> searchByTeamName(String teamName) {
        try {
            return participantRepo.getParticipantByTeamName(teamName);
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }
    }


    public Integer getParticipantSize(int raceId) throws RepositoryException {
        return participantRepo.getParticipantSizeByRace(raceId);
    }
    public int getLastId() throws RepositoryException {
        var participants = participantRepo.getAll().stream()
                .sorted(Comparator.comparingInt(Entity::getId))
                .collect(Collectors.toList());
        Collections.reverse(participants);
        if (participants.isEmpty())
            return 0;
        return participants.get(0).getId();
    }

    public List<Participant> getAll()  {
        try {
            return participantRepo.getAll();
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }

    }
}
