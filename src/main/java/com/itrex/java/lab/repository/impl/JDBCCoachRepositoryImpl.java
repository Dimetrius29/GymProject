package com.itrex.java.lab.repository.impl;

import com.itrex.java.lab.entity.Coach;
import com.itrex.java.lab.entity.Coach;
import com.itrex.java.lab.repository.CoachRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

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

    private DataSource dataSource;

    public JDBCCoachRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Coach> selectAll() {
        List<Coach> coaches = new ArrayList<>();
        try (Connection connection = dataSource.getConnection(); Statement stm = connection.createStatement();
             ResultSet resultSet = stm.executeQuery(SELECT_ALL_QUERY)) {
            while (resultSet.next()) {
                Coach coach = new Coach();
                coach.setId(resultSet.getInt(ID_COLUMN));
                coach.setName(resultSet.getString(NAME_COLUMN));
                coach.setSurname(resultSet.getString(SURNAME_COLUMN));
                coach.setPhone(resultSet.getString(PHONE_COLUMN));
                coach.setSpecialization(resultSet.getString(SPECIALIZATION_COLUMN));
                coach.setPriceOfActivity(resultSet.getDouble(PRICE_OF_ACTIVITY_COLUMN));

                coaches.add(coach);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return coaches;
    }

    @Override
    public Coach selectById(Integer id) {
        Coach coach = new Coach();
        try (Connection connection = dataSource.getConnection();
             Statement stm = connection.createStatement();
             ResultSet resultSet = stm.executeQuery(SELECT_BY_ID_QUERY + id)) {
            if (resultSet.next()) {
                coach.setId(id);
                coach.setName(resultSet.getString(NAME_COLUMN));
                coach.setSurname(resultSet.getString(SURNAME_COLUMN));
                coach.setPhone(resultSet.getString(PHONE_COLUMN));
                coach.setSpecialization(resultSet.getString(SPECIALIZATION_COLUMN));
                coach.setPriceOfActivity(resultSet.getDouble(PRICE_OF_ACTIVITY_COLUMN));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return coach;
    }

    @Override
    public Coach add(Coach coach) {
        try (Connection connection = dataSource.getConnection()) {
            insertCoach(coach, connection);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return coach;
    }

    @Override
    public List<Coach> addAll(List<Coach> coaches) {
        List<Coach> addAllCoach = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try {
                for (Coach coach : coaches) {
                    addAllCoach.add(insertCoach(coach, connection));
                }
                connection.commit();
            } catch (SQLException ex) {
                ex.printStackTrace();
                connection.rollback();
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return addAllCoach;
    }

    @Override
    public Coach update(Coach coach) {
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
            ex.printStackTrace();
        }
        return coach;
    }

    @Override
    public void delete(Integer id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_COACH_QUERY)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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
}
