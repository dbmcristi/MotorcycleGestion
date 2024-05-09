package repository;

import domain.Race;
import utils.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

//@Slf4j
public class RaceRepo implements IRaceRepo<Race, Integer> {

    private JdbcUtils dbUtils;

    public RaceRepo() {
        //log.info("Initializing RaceRepo with properties: {} ",props);
        dbUtils = new JdbcUtils();
    }

    @Override
    public void delete(Integer integer) throws RepositoryException {

    }

    @Override
    public void add(Race e) throws RepositoryException {
        Connection conn = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO race values (?,?,?)")) {
            preparedStatement.setInt(1, e.getId());
            preparedStatement.setString(2, e.getName());
            preparedStatement.setString(3, e.getEngineCapacity().name());
            int rs = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RepositoryException(ex.getMessage());
        }
    }

    @Override
    public Race getById(Integer pos) throws RepositoryException {
        return null;
    }

    @Override
    public void update(Race newEntity) throws RepositoryException {

    }

    @Override
    public void deleteAll() throws RepositoryException {

    }

    @Override
    public List<Race> getAll() throws RepositoryException {
        Connection connection = dbUtils.getConnection();
        ArrayList<Race> raceArrayList = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM race");
            while (resultSet.next()) {
                Race race = new Race(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("engineCapacity"));

                raceArrayList.add(race);
            }
        } catch (SQLException e) {
            //  log.error(e.getMessage());

            throw new RepositoryException(e.getMessage());
        }
        return raceArrayList;
    }

    @Override
    public List<Race> getRaceByEngineSize(String engSize) throws RepositoryException {
        Connection connection = dbUtils.getConnection();
        ArrayList<Race> raceArrayList = new ArrayList<>();
        String sql = "SELECT * FROM race where race.engineCapacity = ?";

        try (PreparedStatement prepStatement = connection.prepareStatement(sql)) {
            prepStatement.setString(1, engSize);

            ResultSet resultSet = prepStatement.executeQuery();
            while (resultSet.next()) {
                Race race = new Race(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("engineCapacity"));

                raceArrayList.add(race);
            }
        } catch (SQLException e) {
            //  log.error(e.getMessage());

            throw new RepositoryException(e.getMessage());
        }
        return raceArrayList;
    }

    @Override
    public boolean isRacePresent(Integer raceId) throws RepositoryException {

        Connection conn = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT id FROM race WHERE id = ?")) {
            preparedStatement.setInt(1, raceId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.getInt(1) > 0) {
                return true;
            }
            return false;
        } catch (SQLException ex) {
            //  log.error(ex.getMessage());

            throw new RepositoryException(ex.getMessage());
        }
    }

}
