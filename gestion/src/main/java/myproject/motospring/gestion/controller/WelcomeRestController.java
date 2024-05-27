package myproject.motospring.gestion.controller;

import myproject.motospring.gestion.dto.ParticipantDto;
import myproject.motospring.gestion.dto.RaceDTO;
import myproject.motospring.gestion.dto.WelcomeDTO;
import myproject.motospring.gestion.service.GestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("plm")
public class WelcomeRestController {
    @Autowired
    private GestionService service;

    @GetMapping("/hello")
    public WelcomeDTO helloWorld(@RequestParam(name = "firstName") String first,
                                 @RequestParam(name = "lastName") String last) {
//        http://localhost:8084/plm/hello?firstName=Cristina&lastName=Dodu
        String message = String.format("Hello %s %s", first, last);
        System.out.println(message);
        return new WelcomeDTO(first, last);
    }

    @GetMapping(value = "/hello2")
    public WelcomeDTO helloWorld2() {
        String message = "Hello2 Spring!";
        System.out.println(message);
        return new WelcomeDTO("ana", "meow");
    }

    @PostMapping(value = "/add")
    public String addHelloWorld2(@RequestBody WelcomeDTO request) {
        String message = String.format("Hello %s %s", request.getFirstName(), request.getLastName());
        System.out.println(message);
        return message;
    }

    @GetMapping("/hello/{first}")
    public WelcomeDTO helloWorldPathParam(@PathVariable String first) {

        String message = String.format("Hello %s", first);
        System.out.println(message);
        return new WelcomeDTO(first, "meow-meow");
    }
}
