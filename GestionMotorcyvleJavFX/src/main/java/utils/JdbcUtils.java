package utils;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class JdbcUtils {

    private Properties jdbcProps;

//    private static final Logger logger= LogManager.getLogger();

    public JdbcUtils() {
        jdbcProps  = new Properties();
        try {
            jdbcProps.load(new FileReader("D:\\FACULTATE_23-24\\mpp23-24\\gitClones\\mpp-proiect-java-dbmcristi\\JDBCUtils"));
        } catch (FileNotFoundException e) {
            //     log.error("Can't find bd.config");
            System.out.println("Can't find file bd.config" + e.getMessage());
            return;
        } catch (IOException e) {
            //    log.error("Can't load bd.config");
            System.out.println("IO exception file bd.config " + e.getMessage());
            return;
        }

    }

    private static Connection instance = null;

    private Connection getNewConnection() {
        //  log.trace("new conn");

        String url = jdbcProps.getProperty("jdbc.url");
        String user = jdbcProps.getProperty("jdbc.user");
        String pass = jdbcProps.getProperty("jdbc.pass");
        //   log.info("trying to connect to database ... {}",url);
//        logger.info("trying to connect to database ... {}",url);
        //      log.info("user: {}",user);
        //      log.info("pass: {}", pass);
        Connection con = null;
        try {

            if (user != null && pass != null)
                con = DriverManager.getConnection(url, user, pass);
            else
                con = DriverManager.getConnection(url);
        } catch (SQLException e) {
            //   log.error(e.getMessage());
            System.out.println("Error getting connection " + e);
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException exp) {
                    throw new RuntimeException(exp);
                }
            }
        }


        return con;
    }

    public Connection getConnection() {
        //   log.trace("gets conn");
        try {
            if (instance == null || instance.isClosed())
                instance = getNewConnection();

        } catch (SQLException e) {
            //     log.error(e.getMessage());
            System.out.println("Error DB " + e);
        }
        //   try {
        //       log.trace(instance.getSchema());
        //  } catch (SQLException e) {
        //      throw new RuntimeException(e);
        //  }
        return instance;
    }

    public  void close() throws SQLException {
        if(instance!=null)
        instance.close();
    }
}
