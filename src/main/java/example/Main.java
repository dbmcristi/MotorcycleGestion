package example;

import domain.Participant;
import domain.Race;
import domain.User;

import repository.ParticipantRepo;
import repository.RaceRepo;
import repository.RepositoryException;
import repository.UserRepo;
import service.ParticipantService;
import service.RaceService;
import service.UserService;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

public class Main {

    public static void main(String[] args) {
       // log.info("Hello world");
//
//        Properties properties = new Properties();
//        try {
//            properties.load(new FileReader("D:\\FACULTATE_23-24\\mpp23-24\\JavaStuff\\GestionMotorcyvleJavFX\\bd.config"));
//        } catch (FileNotFoundException e) {
//       //     log.error("Can't find bd.config");
//            return;
//        } catch (IOException e) {
//        //    log.error("Can't load bd.config");
//            return;
//        }
//
//        RaceRepo raceRepo = new RaceRepo(properties);
//        RaceService raceService = new RaceService(raceRepo);
//
//        ParticipantRepo participantRepo = new ParticipantRepo(properties);
//        ParticipantService participantService = new ParticipantService(participantRepo,raceRepo);
//
//        UserRepo userRepo = new UserRepo(properties);
//        UserService userService = new UserService(userRepo);
//
//        try {
//            int lastIdRace = raceService.getLastId();
//            Race race1 = new Race(lastIdRace+1,"efw","CM_125");
//            Race race2 = new Race(lastIdRace+2,"efw","CM_1000");
//            Race race3 = new Race(lastIdRace+3,"efw","CM_1250");
//
//            raceService.add(race1);
//            raceService.add(race2);
//            raceService.add(race3);
//
//            int lastId = participantService.getLastId();
//            Participant p = new Participant(lastId + 1, "fff", "frgw", 1);
//            try {
//                participantService.addParticipant(p);
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//            Participant p1 = new Participant(lastId + 2, "fff", "frgw", 1);
//            try {
//                participantService.addParticipant(p1);
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//
//            int lastIdUser = userService.getLastId();
//            System.out.println(lastIdUser);
//            User user1 = new User(lastIdUser + 1, "fwef", "fewfwefwf");
//            User user2 = new User(lastIdUser + 2, "gegregetg", "43g2rtg");
//            User user3 = new User(lastIdUser + 3, "get3r", "t5t4g45");
//
//            userService.add(user1);
//            userService.add(user2);
//            userService.add(user3);
//
//            userService.getAll().forEach(e -> System.out.println(e.toString()));
//
//            participantService.getAll().forEach(e -> System.out.println(e.toString()));
//
//            raceService.getAll().forEach(e -> System.out.println(e.toString()));
//
//        } catch (RepositoryException e) {
//            throw new RuntimeException(e);
//        }
    }
}
