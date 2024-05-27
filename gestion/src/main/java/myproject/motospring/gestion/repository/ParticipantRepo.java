package myproject.motospring.gestion.repository;


import myproject.motospring.gestion.domain.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ParticipantRepo extends JpaRepository<Participant, Integer> {

    @Query("SELECT COUNT(p.id) FROM Race r, Participant  p WHERE r.id = p.race.id and p.race.id = ?1")
    Integer getParticipantSizeByRace(int raceId);

    @Query("SELECT p from Participant p where p.teamName = ?1")
    List<Participant> getParticipantByTeamName(String teamName);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Participant p set p.team_name = :teamName, p.name = :name, p.id_race = :idRace where p.id = :id", nativeQuery = true)
    void setParticipantInfoById(@Param("id") Integer id, @Param("teamName") String teamName, @Param("name") String name, @Param("idRace") Integer idRace);
}
