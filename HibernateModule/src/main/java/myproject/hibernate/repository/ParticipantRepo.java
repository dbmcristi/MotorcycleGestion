package myproject.hibernate.repository;

import myproject.hibernate.domain.Participant;
import myproject.hibernate.domain.Race;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Objects;

public class ParticipantRepo implements ICrudRepository<Integer, Participant> {

    static SessionFactory sessionFactory;
    // static Session session;

    public ParticipantRepo() {
        sessionFactory = HibernateUtils.getSessionFactory();
        //  session = HibernateUtils.getCurrentSessionFromConfig();
    }

    @Override
    public Participant save(Participant participant) {
        return null;
    }

    public Participant save(Integer idRace, Participant participant) {
        sessionFactory.inTransaction(session -> {
            Race race = session.get(Race.class, idRace);
            participant.setRace(race);
            session.persist(participant);
            System.out.println("User added " + participant);
        });
        return participant;
    }

    @Override
    public Participant update(Integer idRace, Participant participant) {
        sessionFactory.inTransaction(session -> {
            Race race = session.get(Race.class, idRace);
            participant.setRace(race);
            session.merge(participant);
            session.flush();
            System.out.println("User added " + participant);
        });
        return participant;
    }

    @Override
    public void delete(Integer id) {
        sessionFactory.inTransaction(session -> {
            Participant r = session.find(Participant.class, id);
            if (!Objects.isNull(r)) {
                System.out.println("Found Participant with id = " + id);
                session.remove(r);
                session.flush();
                System.out.println("deleted with success");
            }
        });
    }

    @Override
    public Participant findOne(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            Participant p = session.createQuery("from Participant where id=:param", Participant.class)
                    .setParameter("param", id)
                    .getSingleResultOrNull();
            System.out.println("Participant: " + p);
            return p;
        }
    }

    @Override
    public List<Participant> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Participant", Participant.class).getResultList();
        }
    }

    public Integer getParticipantSizeByRace(int raceId) {
//        String sql = "select count(p.id) from race r, participant p where r.id = p.idRace and p.idRace = ?;";
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("select count(p.id) from Race r, Participant p where r.id =p.race.id and p.race.id =: param", Participant.class)
                    .setParameter("param", raceId);
            Long size = (Long) query.uniqueResult();

            return Integer.valueOf(String.valueOf(size));
        }
    }

    public List<Participant> getParticipantByTeamName(String teamName) {

        try (Session session = sessionFactory.openSession()) {
            List<Participant> p = session.createQuery("from Participant p where p.teamName =: param", Participant.class)
                    .setParameter("param", teamName)
                    .getResultList();
            return p;
        }
    }
}
