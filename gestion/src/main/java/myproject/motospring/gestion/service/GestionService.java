package myproject.motospring.gestion.service;


import myproject.motospring.gestion.domain.*;
import myproject.motospring.gestion.dto.*;

import myproject.motospring.gestion.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GestionService {
    @Autowired
    ParticipantRepo participantRepo;

    @Autowired
    RaceRepo raceRepo;

    public RaceDTO saveRace(RaceDTO dto) {
        Race u = new Race(dto.getName(), dto.getEngineCapacity());
        Race out = raceRepo.save(u);
        return new RaceDTO(u.getId(), u.getName(), u.getEngineCapacity());
    }

    public RaceDTO updateRace(RaceDTO dto) {
//        TODO
//        getRferenceByID, updateEntity, saveEntityDinDTO
        Race u = new Race(dto.getName(), dto.getEngineCapacity());
        Race out = raceRepo.save(u);
        return new RaceDTO(u.getId(), u.getName(), u.getEngineCapacity());
    }

    public ParticipantDto saveParticipant(ParticipantDto p) {
        Participant participant = new Participant(p.getTeamName(), p.getName());
        Race race = raceRepo.getReferenceById(p.getIdRace());
        participant.setRace(race);
        var par = participantRepo.save(participant);
        ParticipantDto saved = new ParticipantDto(par.getId(), par.getTeamName(), par.getName(), par.getRace().getId());
        return saved;
    }


    public List<RaceDTO> getAllRace() {
        return raceRepo.findAll().stream()
                .map(entity -> new RaceDTO(entity.getId(), entity.getName(), entity.getEngineCapacity()))
                .collect(Collectors.toList());
    }

    public List<ParticipantDto> getAllParticipant() {
        return participantRepo.findAll().stream()
                .map(entity -> new ParticipantDto(entity.getId(), entity.getTeamName(), entity.getName(), entity.getRace().getId()))
                .collect(Collectors.toList());
    }

    public RaceDTO getRaceById(Integer id) {
        var par = raceRepo.getReferenceById(id);
        RaceDTO saved = new RaceDTO(par.getId(), par.getName(), par.getEngineCapacity());
        return saved;
    }

    public ParticipantDto getParticipantById(Integer id) {
        var par = participantRepo.getReferenceById(id);
        ParticipantDto saved = new ParticipantDto(par.getId(), par.getTeamName(), par.getName(), par.getRace().getId());
        return saved;
    }

    // metode ui
    public List<RaceDTO> showRacesByEngineSize(String engineCapacity) {
        return raceRepo.getRaceByEngineSize(engineCapacity).stream()
                .map(entity -> new RaceDTO(entity.getId(), entity.getName(), entity.getEngineCapacity()))
                .collect(Collectors.toList());
    }

    public List<ParticipantDto> searchByTeamName(String teamName) {
        return participantRepo.getParticipantByTeamName(teamName).stream()
                .map(entity -> new ParticipantDto(entity.getId(), entity.getTeamName(), entity.getName(), entity.getRace().getId()))
                .collect(Collectors.toList());
    }

    public Integer getParticipantSize(int raceId) {
        return participantRepo.getParticipantSizeByRace(raceId);

    }

    public void deleteRace(Integer id) {
        raceRepo.deleteById(id);
    }

    public void deleteParticipant(Integer id) {
        participantRepo.deleteById(id);
    }

    public ParticipantDto setParticipantInfoById(Participant p, Integer raceId) {
        participantRepo.setParticipantInfoById(p.getId(), p.getTeamName(), p.getName(), raceId);
        var participant = participantRepo.getReferenceById(p.getId());
        return new ParticipantDto(participant.getId(), participant.getTeamName(), participant.getName(), participant.getRace().getId());
    }

    public RaceDTO setRaceInfoById(Race p) {
        raceRepo.setRaceInfoById(p.getId(), p.getName(), p.getEngineCapacity());
        var race = raceRepo.getReferenceById(p.getId());
        return new RaceDTO(race.getId(),race.getName(), race.getEngineCapacity());
    }

}
