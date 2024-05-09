package myproject.hibernate.repository;

import myproject.hibernate.domain.Race;
import myproject.hibernate.domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Objects;

public class RaceRepo implements ICrudRepository<Integer, Race> {
    static SessionFactory sessionFactory;

    public RaceRepo() {
        sessionFactory = HibernateUtils.getSessionFactory();
    }

    @Override
    public Race save(Race r) {
        sessionFactory.inTransaction(session -> {
            session.persist(r);
            System.out.println("Race added " + r);
        });
        return r;
    }

    @Override
    public void delete(Integer id) {
        sessionFactory.inTransaction(session -> {
            Race r = session.find(Race.class, id);
            if (!Objects.isNull(r)) {
                System.out.println("Found Race with id = " + id);
                session.remove(r);
                session.flush();
                System.out.println("deleted with success");
            }
        });
    }

    @Override
    public Race findOne(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            Race race = session.createSelectionQuery("from Race where id=:param ", Race.class)
                    .setParameter("param", id)
                    .getSingleResultOrNull();
            System.out.println("race: " + race);
            return race;
        }
    }

    @Override
    public Race update(Integer id, Race race) {
        sessionFactory.inTransaction(session -> {
            if (!Objects.isNull(session.find(User.class, id))) {
                System.out.println("Found Race with id = " + id);
                session.merge(race);
                session.flush();
                System.out.println("merged with success");
            }
        });
        return race;
    }


    @Override
    public List<Race> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Race", Race.class).getResultList();
        }
    }

    public List<Race> getRaceByEngineSize(String engineCapacity) {
        try (Session session = sessionFactory.openSession()) {
            List<Race> resultList = session.createQuery("from Race r where r.engineCapacity =: param", Race.class)
                    .setParameter("param", engineCapacity).getResultList();
            return resultList;
        }
    }
}
