package repository;

import domain.Participant;
import utils.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

//@Slf4j
public class ParticipantRepo implements IParticipantRepo<Participant, Integer> {
    private JdbcUtils dbUtils;


    public ParticipantRepo() {
        // log.info("Initializing ParticipantRepo with properties: {} ", props);
        dbUtils = new JdbcUtils();
    }

    @Override
    public void delete(Integer integer) throws RepositoryException {
    }

    @Override
    public void add(Participant e) throws RepositoryException {
        //log.trace("Adding participant: " );
        Connection conn = dbUtils.getConnection();
        try (PreparedStatement prepStatement = conn.prepareStatement("INSERT INTO participant VALUES (?,?,?,?)");) {
            prepStatement.setInt(1, e.getId());
            prepStatement.setString(2, e.getName());
            prepStatement.setString(3, e.getTeamName());
            prepStatement.setInt(4, e.getIdRace());
            int rs = prepStatement.executeUpdate();
        } catch (SQLException ex) {
            //  log.error(ex.getMessage());
            throw new RepositoryException(ex.getMessage());
        }

    }

    @Override
    public Participant getById(Integer pos) throws RepositoryException {
        return null;
    }

    @Override
    public void update(Participant newEntity) throws RepositoryException {

    }

    @Override
    public void deleteAll() throws RepositoryException {

    }

    @Override
    public List<Participant> getAll() throws RepositoryException {
        Connection connection = dbUtils.getConnection();
        ArrayList<Participant> participantArrayList = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM participant");
            while (resultSet.next()) {
                Participant participant = new Participant(resultSet.getInt("id"),
                        resultSet.getString("teamName"),
                        resultSet.getString("name"),
                        resultSet.getInt("idRace"));
                participantArrayList.add(participant);
            }
        } catch (SQLException e) {
            //   log.error(e.getMessage());

            throw new RepositoryException(e.getMessage());

        }
//        participantArrayList.stream().forEach(Participant::toString);
        return participantArrayList;
    }
    @Override
    public Integer getParticipantSizeByRace(int raceId) throws RepositoryException {
        String sql = "select count(p.id) from race r, participant p where r.id = p.idRace and p.idRace = ?;";
        Connection conn = dbUtils.getConnection();

        try {
            var stmt = conn.prepareStatement(sql);
            stmt.setInt(1, raceId);
            ResultSet rs = stmt.executeQuery();
            return rs.getInt(1);

        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }
    @Override
    public List<Participant> getParticipantByTeamName(String teamName) throws RepositoryException {
        Connection connection = dbUtils.getConnection();
        ArrayList<Participant> participantArrayList = new ArrayList<>();
        String sql = "SELECT * FROM participant where teamName = ?";

        try (final PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, teamName);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Participant participant = new Participant(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("teamName"),
                        resultSet.getInt("idRace"));
                participantArrayList.add(participant);
            }
        } catch (SQLException e) {
            //   log.error(e.getMessage());
            throw new RepositoryException(e.getMessage());
        }
        return participantArrayList;
    }
    @Override
    public Integer showParticipNrByRaceName(String raceName) throws RepositoryException {
        Connection connection = dbUtils.getConnection();
        ArrayList<Participant> participantArrayList = new ArrayList<>();
        //String sql = "SELECT p.id,p.name,p.teamName,p.idRace FROM participant p,race r where p.idRace=r.id and r.name = ?";
        String sql = "SELECT count(p.id) as nr FROM participant p,race r where p.idRace=r.id and r.name = ?";
        try (final PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, raceName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                  return  resultSet.getInt("nr");
            }
            return 0;
        } catch (SQLException e) {
            //   log.error(e.getMessage());
            throw new RepositoryException(e.getMessage());
        }
    }
}
