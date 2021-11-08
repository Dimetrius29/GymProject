package com.itrex.java.lab.repository.impl.jdbc;

import com.itrex.java.lab.entity.Coach;
import com.itrex.java.lab.exception.GymException;
import com.itrex.java.lab.repository.CoachRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

@Repository
@Qualifier("JDBCCoachRepository")
public class JDBCCoachRepositoryImpl implements CoachRepository {

    private static final String ID_COLUMN = "id";
    private static final String PHONE_COLUMN = "phone";
    private static final String NAME_COLUMN = "name";
    private static final String SURNAME_COLUMN = "surname";
    private static final String SPECIALIZATION_COLUMN = "specialization";
    private static final String PRICE_OF_ACTIVITY_COLUMN = "price_of_activity";

    private static final String SELECT_ALL_QUERY = "SELECT * FROM coach";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM coach WHERE id=";
    private static final String INSERT_COACH_QUERY = "INSERT INTO coach(name, surname, phone, specialization, price_of_activity) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_COACH_QUERY = "UPDATE coach SET name=?, surname=?, phone=?, specialization=?, price_of_activity=? WHERE id=?";
    private static final String DELETE_COACH_QUERY = "DELETE FROM coach WHERE id=?";
    private static final String DELETE_TRAINING_BY_COACH_ID = "DELETE FROM training WHERE coach_id=?";

    private DataSource dataSource;

    public JDBCCoachRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Coach> selectAll() throws GymException {
        List<Coach> coaches = new ArrayList<>();
        try (Connection connection = dataSource.getConnection(); Statement stm = connection.createStatement();
             ResultSet resultSet = stm.executeQuery(SELECT_ALL_QUERY)) {
            while (resultSet.next()) {
                Coach coach = getCoach(resultSet);
                coaches.add(coach);
            }
        } catch (SQLException ex) {
            throw new GymException("SELECT ALL COACHES EXCEPTION: ", ex);
        }
        return coaches;
    }

    @Override
    public Optional<Coach> selectById(Integer id) throws GymException {
        Coach coach = null;
        try (Connection connection = dataSource.getConnection();
             Statement stm = connection.createStatement();
             ResultSet resultSet = stm.executeQuery(SELECT_BY_ID_QUERY + id)) {
            if (resultSet.next()) {
                coach = getCoach(resultSet);
            }
        } catch (SQLException ex) {
            throw new GymException("SELECT COACH BY ID EXCEPTION: ", ex);
        }
        return Optional.ofNullable(coach);
    }

    @Override
    public Coach add(Coach coach) throws GymException {
        try (Connection connection = dataSource.getConnection()) {
            insertCoach(coach, connection);
        } catch (SQLException ex) {
            throw new GymException("ADD COACH EXCEPTION: ", ex);
        }
        return coach;
    }

    @Override
    public void addAll(List<Coach> coaches) throws GymException {
        List<Coach> addAllCoach = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try {
                for (Coach coach : coaches) {
                    addAllCoach.add(insertCoach(coach, connection));
                }
                connection.commit();
            } catch (SQLException ex) {
                connection.rollback();
                throw new SQLException("CONNECTION ROLLBACK: ", ex);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            throw new GymException("ADD ALL COACHES FAILED: ", ex);
        }
    }

    @Override
    public Coach update(Coach coach) throws GymException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_COACH_QUERY)) {
            preparedStatement.setString(1, coach.getName());
            preparedStatement.setString(2, coach.getSurname());
            preparedStatement.setString(3, coach.getPhone());
            preparedStatement.setString(4, coach.getSpecialization());
            preparedStatement.setDouble(5, coach.getPriceOfActivity());
            preparedStatement.setInt(6, coach.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new GymException("UPDATE COACH EXCEPTION: ", ex);
        }
        return coach;
    }

    @Override
    public void deleteCoach(Integer id) throws GymException {
        deleteTrainingByCoach(id);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_COACH_QUERY)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new GymException("DELETE COACH EXCEPTION: ", ex);
        }
    }

    private void deleteTrainingByCoach(Integer coachId) throws GymException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TRAINING_BY_COACH_ID)) {
            preparedStatement.setInt(1, coachId);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new GymException("DELETE USER FROM TRAINING EXCEPTION: ", ex);
        }
    }

    private Coach insertCoach(Coach coach, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COACH_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, coach.getName());
            preparedStatement.setString(2, coach.getSurname());
            preparedStatement.setString(3, coach.getPhone());
            preparedStatement.setString(4, coach.getSpecialization());
            preparedStatement.setDouble(5, coach.getPriceOfActivity());

            final int effectiveRows = preparedStatement.executeUpdate();

            if (effectiveRows == 1) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        coach.setId(generatedKeys.getInt(ID_COLUMN));
                    }
                }
            }
        }
        return coach;
    }

    private Coach getCoach(ResultSet resultSet) throws SQLException {
        Coach coach = new Coach();
        coach.setId(resultSet.getInt(ID_COLUMN));
        coach.setName(resultSet.getString(NAME_COLUMN));
        coach.setSurname(resultSet.getString(SURNAME_COLUMN));
        coach.setPhone(resultSet.getString(PHONE_COLUMN));
        coach.setSpecialization(resultSet.getString(SPECIALIZATION_COLUMN));
        coach.setPriceOfActivity(resultSet.getDouble(PRICE_OF_ACTIVITY_COLUMN));

        return coach;
    }
}