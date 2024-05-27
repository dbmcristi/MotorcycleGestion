package myproject.motospring.gestion.controller;

import jakarta.servlet.http.Part;
import myproject.motospring.gestion.domain.Participant;
import myproject.motospring.gestion.domain.Race;
import myproject.motospring.gestion.dto.ParticipantDto;
import myproject.motospring.gestion.dto.RaceDTO;
import myproject.motospring.gestion.service.GestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("participant")
public class ParticipantController {
    @Autowired
    private GestionService service;

    @PostMapping(value = "/")
    public ParticipantDto addParticipant(@RequestBody ParticipantDto request) {
        return service.saveParticipant(request);
    }

    @PutMapping(value = "/")
    public ParticipantDto updateParticipant(@RequestBody ParticipantDto request) {
        Participant p = new Participant(request.getTeamName(),request.getName());
        p.setId(request.getId());
        return service.setParticipantInfoById(p, request.getIdRace());
    }

    @GetMapping(value = "/{id}")
    public ParticipantDto getParticipant(@PathVariable Integer id) {
        return service.getParticipantById(id);
    }

    @GetMapping(value = "/all")
    public List<ParticipantDto> getAllParticipant() {
        return service.getAllParticipant();
    }

    @GetMapping(value = "/raceId/{raceId}/size")
    public Integer getParticipantSize(@PathVariable Integer raceId) {
        return service.getParticipantSize(raceId);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteParticipant(@PathVariable Integer id) {
        service.deleteParticipant(id);
    }

    @GetMapping(value = "/search")
    public List<ParticipantDto> searchByTeamName(@RequestParam(name = "teamName") String teamName) {
        return service.searchByTeamName(teamName);
    }
}
