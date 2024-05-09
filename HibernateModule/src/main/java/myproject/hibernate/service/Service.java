package myproject.hibernate.service;

import myproject.hibernate.domain.Participant;
import myproject.hibernate.domain.Race;
import myproject.hibernate.domain.User;
import myproject.hibernate.repository.ParticipantRepo;
import myproject.hibernate.repository.RaceRepo;
import myproject.hibernate.repository.UserRepo;

import java.util.List;

public class Service {
    ParticipantRepo participantRepo;
    RaceRepo raceRepo;
    UserRepo userRepo;

    public Service() {
        participantRepo = new ParticipantRepo();
        userRepo = new UserRepo();
        raceRepo = new RaceRepo();
    }

    public User addUser(User u) {
        return userRepo.save(u);
    }

    public Race addRace(Race u) {
        return raceRepo.save(u);
    }

    public Participant addParticipant(Participant p, Integer idRace) {
        return participantRepo.save(idRace, p);
    }

    public Participant updateParticipant(Participant p, Integer idRace) {
        return participantRepo.update(idRace, p);
    }

    public List<Race> getAllRace() {
        return raceRepo.getAll();
    }

    public List<User> getAllUser() {
        return userRepo.getAll();
    }

    public List<Participant> getAllParticipant() {
        return participantRepo.getAll();
    }

    public Race getRaceById(Integer id) {
        return raceRepo.findOne(id);
    }

    public Participant getParticipantById(Integer id) {
        return participantRepo.findOne(id);
    }

    public User getUserById(Integer id) {
        return userRepo.findOne(id);
    }

    // metode ui
    public List<Race> showRacesByEngineSize(String engineCapacity) {
        return raceRepo.getRaceByEngineSize(engineCapacity);
    }

    public List<Participant> searchByTeamName(String teamName) {
        return participantRepo.getParticipantByTeamName(teamName);
    }


    public Integer getParticipantSize(int raceId) {
        return participantRepo.getParticipantSizeByRace(raceId);

    }

    public boolean isLogged(String usrn, String pwsd) {
        return userRepo.isLogged(usrn, pwsd);

    }

    public User updateUser(User e) {
        return userRepo.update(e.getId(), e);
    }

    public Race updateRace(Race r) {
        return raceRepo.update(r.getId(), r);
    }

    public void deleteUser(User e) {
        userRepo.delete(e.getId());
    }

    public void deleteRace(Race r) {
        raceRepo.delete(r.getId());
    }

    public void deleteParticipant(Participant p) {
        participantRepo.delete(p.getId());
    }
}
