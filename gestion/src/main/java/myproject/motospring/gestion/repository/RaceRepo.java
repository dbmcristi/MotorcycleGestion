package myproject.motospring.gestion.repository;

import myproject.motospring.gestion.domain.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RaceRepo extends JpaRepository<Race, Integer> {
    @Query("SELECT r FROM Race r WHERE r.engineCapacity = ?1")
    List<Race> getRaceByEngineSize(String engineCapacity) ;

    @Transactional
    @Modifying
    @Query(value="update RACE r set r.NAME = :name, r.ENGINE_CAPACITY  = :engineCapacity where r.ID = :id", nativeQuery = true)
    void setRaceInfoById(@Param("id")Integer id,@Param("name")String name, @Param("engineCapacity")String engineCapacity);

}