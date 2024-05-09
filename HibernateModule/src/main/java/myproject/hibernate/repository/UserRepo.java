package myproject.hibernate.repository;


import myproject.hibernate.domain.Participant;
import myproject.hibernate.domain.User;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Objects;

import org.hibernate.Session;
import org.hibernate.query.Query;

//@Slf4j
public class UserRepo implements ICrudRepository<Integer, User> {
    static SessionFactory sessionFactory;

    public UserRepo() {
        System.out.println("Starting Hibernate - UserRepo");
        sessionFactory = HibernateUtils.getSessionFactory();
    }

    @Override
    public User save(User user) {
        sessionFactory.inTransaction(session -> session.persist(user));
        System.out.println(user + " saved");
        return user;
    }

    public void delete(Integer id) {
        sessionFactory.inTransaction(session -> {
            User user = findOne(id);
            if (user != null) {
                session.remove(user);
                session.flush();
                System.out.println(user + " deleted");
            }
        });

    }

    @Override
    public User findOne(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.createSelectionQuery("from User where id=:param ", User.class)
                    .setParameter("param", id)
                    .getSingleResultOrNull();
            System.out.println("user: " + user);
            return user;
        }
    }

    public User findById(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.find(User.class, id);
            System.out.println("user: " + user);
            return user;
        }
    }

    @Override
    public User update(Integer id, User user) {
        sessionFactory.inTransaction(session -> {
            if (!Objects.isNull(session.find(User.class, id))) {
                System.out.println("Found User with id = " + id);
                session.merge(user);
                session.flush();
                System.out.println("merged with success");
            }
        });
        return user;
    }


    @Override
    public List<User> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from User ", User.class).getResultList();
        }
    }

    public boolean isLogged(String usrn, String pwsd) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("select count(u.id) from User u where u.username =: param1 and u.password =: param2", User.class)
                    .setParameter("param1", usrn)
                    .setParameter("param2", pwsd);
            Long size = (Long) query.uniqueResult();
            return size > 0;
        }
    }
}
