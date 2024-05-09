package myproject.hibernate;

import myproject.hibernate.domain.EngineCapacity;
import myproject.hibernate.domain.Participant;
import myproject.hibernate.domain.Race;
import myproject.hibernate.domain.User;
import myproject.hibernate.repository.RepositoryException;
import myproject.hibernate.service.Service;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Service service = new Service();
        User e = new User("Ana", "Basnana123");
        Race r = new Race("Raliul Oradei", EngineCapacity.CM_125.name());
        Participant p = new Participant("Mama", "Boss");

            /*
//            addUser
            User user = service.addUser(e);
//            getUserByID
            System.out.println(service.getUserById(user.getId()));
//          addRace
            Race race = service.addRace(r);
//            getRaceById
            System.out.println(service.getRaceById(race.getId()));
//              addParticipant
            Participant participant = service.addParticipant(p, race.getId());
//          getParticipantById
            System.out.println(service.getParticipantById(participant.getId()));
//          updateParticipant
            participant.setName("updated");
            Participant participantUpdated = service.updateParticipant(participant, 2);
//          getParticipantById
            System.out.println(service.getParticipantById(participantUpdated.getId()));
*/
//           service.getAllUser().stream().forEach(usr -> System.out.println(usr.toString()));
//           service.getAllRace().stream().forEach(rce -> System.out.println(rce.toString()));
//           service.getAllParticipant().stream().forEach(prt -> System.out.println(prt.toString()));

//
 //       service.deleteUser(service.getUserById(1));
 //       service.deleteRace(service.getRaceById(1));
  //      service.deleteParticipant(service.getParticipantById(1));

//        service.searchByTeamName("Mama").forEach(out -> System.out.println(out.toString()));
//         service.showRacesByEngineSize("CM_500").forEach(out -> System.out.println(out.toString()));
//        System.out.println(service.isLogged("capy","meow"));
//        System.out.println(service.getParticipantSize(2));
    }
}