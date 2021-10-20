package com.itrex.java.lab.repository.impl;

import com.itrex.java.lab.entity.Training;
import com.itrex.java.lab.entity.User;
import com.itrex.java.lab.entity.Coach;
import com.itrex.java.lab.repository.UserRepository;
import com.itrex.java.lab.repository.CoachRepository;
import com.itrex.java.lab.repository.TrainingRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class JDBCTrainingRepositoryImpl implements TrainingRepository{

    private static final String USER_ID_COLUMN = "user_id";
    private static final String COACH_ID_COLUMN = "coach_id";
    private static final String DATE_INFO_COLUMN = "date_info";
    private static final String START_TIME_COLUMN = "start_time";
    private static final String END_TIME_COLUMN = "end_time";

    private static final String SELECT_ALL_QUERY = "SELECT * FROM training";
    private static final String INSERT_TRAINING_QUERY = "INSERT INTO training(user_id, coach_id, date_info, start_time, end_time) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_TRAINING_QUERY = "UPDATE training SET user_id=?, coach_id=?, date_info=?, start_time=?, end_time=? WHERE user_id=? AND role_id=?";
    private static final String DELETE_TRAINING_QUERY = "DELETE FROM training WHERE user_id=?";

    private DataSource dataSource;

    public JDBCTrainingRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Training> selectAll() {
        List<Training> trainings = new ArrayList<>();
        try (Connection connection = dataSource.getConnection(); Statement stm = connection.createStatement();
             ResultSet resultSet = stm.executeQuery(SELECT_ALL_QUERY)) {
            while (resultSet.next()) {
                Training training = new Training();
                UserRepository userRepository = new JDBCUserRepositoryImpl(dataSource);
                CoachRepository coachRepository = new JDBCCoachRepositoryImpl(dataSource);
                training.setUser(userRepository.selectById(resultSet.getInt(USER_ID_COLUMN)));
                training.setCoach(coachRepository.selectById(resultSet.getInt(COACH_ID_COLUMN)));
                training.setDate(resultSet.getDate(DATE_INFO_COLUMN));
                training.setStartTime(resultSet.getTime(START_TIME_COLUMN));
                training.setEndTime(resultSet.getTime(END_TIME_COLUMN));

                trainings.add(training);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return trainings;
    }

    @Override
    public Training add(Training training) {
        try (Connection connection = dataSource.getConnection()) {
            insertTraining(training, connection);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return training;
    }

    @Override
    public List<Training> addAll(List<Training> trainings) {
        List<Training> addAllTraining = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try {
                for (Training training : trainings) {
                    addAllTraining.add(insertTraining(training, connection));
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
        return addAllTraining;
    }

    @Override
    public Training update(Training training, User user, Coach coach) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TRAINING_QUERY)) {
            preparedStatement.setDate(1, (Date) training.getDate());
            preparedStatement.setTime(2, training.getStartTime());
            preparedStatement.setTime(3, training.getEndTime());
            preparedStatement.setInt(4, training.getUser().getId());
            preparedStatement.setInt(5, training.getCoach().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return training;
    }

    @Override
    public void delete(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TRAINING_QUERY)) {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Training insertTraining(Training training, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TRAINING_QUERY)) {
            preparedStatement.setInt(1, training.getUser().getId());
            preparedStatement.setInt(2, training.getCoach().getId());
            preparedStatement.setDate(3, (Date) training.getDate());
            preparedStatement.setTime(4, training.getStartTime());
            preparedStatement.setTime(5, training.getEndTime());
            preparedStatement.executeUpdate();
        }
        return training;
    }
}