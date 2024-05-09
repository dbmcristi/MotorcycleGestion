package repository;

import domain.User;
import utils.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

//@Slf4j
public class UserRepo implements IUserRepo<User, Integer> {

    private JdbcUtils dbUtils;
    public UserRepo() {
        dbUtils=new JdbcUtils();
    }
    @Override
    public void delete(Integer integer) throws RepositoryException {

    }

    @Override
    public void add(User e) throws RepositoryException {
        Connection conn = dbUtils.getConnection();
        try(PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO user values (?,?,?)")){
            preparedStatement.setInt(1,e.getId());
            preparedStatement.setString(2,e.getUsername());
            preparedStatement.setString(3, e.getPassword());
            int rs = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RepositoryException(ex.getMessage());
        }
    }

    @Override
    public User getById(Integer pos) throws RepositoryException {
        return null;
    }

    @Override
    public void update(User newEntity) throws RepositoryException {

    }

    @Override
    public void deleteAll() throws RepositoryException {

    }

    @Override
    public ArrayList<User> getAll() throws RepositoryException {
        Connection connection = dbUtils.getConnection();
        ArrayList<User> userList = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user");
            while (resultSet.next()) {
                User user = new User
                        (resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"));
                userList.add(user);
            }
        } catch (SQLException e) {
           // log.error(e.getMessage());
            throw new RepositoryException(e.getMessage());
        }
        return userList;
    }
    @Override
    public boolean isLogged(String usrn, String pwsd) throws RepositoryException {
        Connection conn = dbUtils.getConnection();
        System.out.println(conn);

        String sql = "select count(id) from user where username = ? and password = ?";

        try {
            var stmt = conn.prepareStatement(sql);
            stmt.setString(1, usrn);
            stmt.setString(2, pwsd);
            ResultSet rs = stmt.executeQuery();

            if (rs.getInt(1) == 1) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RepositoryException(" err", e);
        }
    }
}
