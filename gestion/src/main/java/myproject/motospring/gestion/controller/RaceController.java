package myproject.motospring.gestion.controller;

import myproject.motospring.gestion.domain.Race;
import myproject.motospring.gestion.dto.ParticipantDto;
import myproject.motospring.gestion.dto.RaceDTO;
import myproject.motospring.gestion.service.GestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("race")
public class RaceController {
    @Autowired
    private GestionService service;

    @PostMapping(value = "/")
    public RaceDTO addRace(@RequestBody RaceDTO request) {
        return service.saveRace(request);
    }

    @PutMapping(value = "/")
    public RaceDTO updateRace(@RequestBody Race request) {
       return service.setRaceInfoById(request);
    }

    @GetMapping(value = "/all")
    public List<RaceDTO> getAllRace() {
        return service.getAllRace();
    }

    @DeleteMapping(value = "/{id}")
    public void deleteRace(@PathVariable Integer id) {
        service.deleteRace(id);
    }

    @GetMapping(value = "/{id}")
    public RaceDTO getRace(@PathVariable Integer id) {
        return service.getRaceById(id);
    }

    @GetMapping(value = "/search")
    public List<RaceDTO> showRacesByEngineSize(@RequestParam(name = "engineSize") String engineSize) {
        return service.showRacesByEngineSize(engineSize);
    }

}
