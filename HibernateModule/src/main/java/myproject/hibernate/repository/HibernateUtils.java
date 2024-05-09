package myproject.hibernate.repository;

import myproject.hibernate.domain.Participant;
import myproject.hibernate.domain.Race;
import myproject.hibernate.domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.HashMap;
import java.util.Map;

public class HibernateUtils {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory(){
        if ((sessionFactory==null)||(sessionFactory.isClosed()))
            sessionFactory=createNewSessionFactory();
        return sessionFactory;
    }

    private static  SessionFactory createNewSessionFactory(){
        sessionFactory = new Configuration()
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Participant.class)
                .addAnnotatedClass(Race.class)
                .buildSessionFactory();
        return sessionFactory;
    }

    public static  void closeSessionFactory(){
        if (sessionFactory!=null)
            sessionFactory.close();
    }
//
//    public static Session getCurrentSessionFromConfig() {
//        Map<String, Object> settings = new HashMap<>();
//        settings.put("connection.driver_class", "org.sqlite.JDBC");
//        settings.put("dialect", "org.hibernate.dialect.SQLiteDialect");
//        settings.put("hibernate.connection.url",
//                "jdbc:sqlite:D:/FACULTATE_23-24/mpp23-24/gitClones/mpp-proiect-java-dbmcristi/hibernate.sqlite");
//
//      //  settings.put("hibernate.connection.username", "root");
//      //  settings.put("hibernate.connection.password", "password");
//        settings.put("hibernate.current_session_context_class", "thread");
//        settings.put("hibernate.show_sql", "true");
//        settings.put("hibernate.format_sql", "true");
//
//        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
//                .applySettings(settings).build();
//
//        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
//        metadataSources.addAnnotatedClass(User.class);
//        metadataSources.addAnnotatedClass(Participant.class);
//        metadataSources.addAnnotatedClass(Race.class);
//        Metadata metadata = metadataSources.buildMetadata();
//
//        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
//        Session session = sessionFactory.getCurrentSession();
//        return session;
//    }
}
