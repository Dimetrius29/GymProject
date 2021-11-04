package com.itrex.java.lab.repository.impl.jdbc;

import com.itrex.java.lab.entity.User;
import com.itrex.java.lab.repository.UserRepository;
import com.itrex.java.lab.exception.GymException;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

@Repository
public class JDBCUserRepositoryImpl implements UserRepository {
    private static final String ID_COLUMN = "id";
    private static final String LOGIN_COLUMN = "login";
    private static final String PASSWORD_COLUMN = "password";
    private static final String NAME_COLUMN = "name";
    private static final String SURNAME_COLUMN = "surname";
    private static final String PHONE_COLUMN = "phone";

    private static final String SELECT_ALL_QUERY = "SELECT * FROM user";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM user WHERE id=";
    private static final String SELECT_ALL_USERS_BY_ROLE = "SELECT * FROM user WHERE id IN (SELECT user_id FROM user_role WHERE role_id IN (SELECT id FROM role WHERE name = ?))";
    private static final String ASSIGN_USER_ROLE_QUERY = "INSERT INTO user_role(user_id, role_id) VALUES (?, ?)";
    private static final String INSERT_USER_QUERY = "INSERT INTO user(login, password, name, surname, phone) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_USER_QUERY = "UPDATE user SET login=?, password=?, name=?, surname=?, phone=? WHERE id=?";
    private static final String DELETE_USER_QUERY = "DELETE FROM user WHERE id=?";
    private static final String DELETE_USER_FROM_USER_ROLE_QUERY = "DELETE FROM user_role WHERE user_id=?";
    private static final String DELETE_USER_FROM_TRAINING_QUERY = "DELETE FROM training WHERE user_id=?";

    private DataSource dataSource;

    public JDBCUserRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<User> selectAll() throws GymException {
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement stm = connection.createStatement();
             ResultSet resultSet = stm.executeQuery(SELECT_ALL_QUERY)) {
            while (resultSet.next()) {
                User user = getUser(resultSet);
                users.add(user);
            }
        } catch (SQLException ex) {
            throw new GymException("SELECT ALL USERS EXCEPTION: ", ex);
        }
        return users;
    }

    @Override
    public Optional<User> selectById(Integer id) throws GymException {
        User user = null;
        try (Connection connection = dataSource.getConnection();
             Statement stm = connection.createStatement();
             ResultSet resultSet = stm.executeQuery(SELECT_BY_ID_QUERY + id)) {
            if (resultSet.next()) {
                user = getUser(resultSet);
            }
        } catch (SQLException ex) {
            throw new GymException("SELECT USER BY ID EXCEPTION: ", ex);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<List> getAllUsersByRole(String roleName) throws GymException {
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS_BY_ROLE)) {
            preparedStatement.setString(1, roleName);
            ResultSet resultSet = preparedStatement.executeQuery();
            {
                while (resultSet.next()) {
                    User user = getUser(resultSet);
                    users.add(user);
                }
            }
        } catch (SQLException ex) {
            throw new GymException("GET ALL USER BY ROLE EXCEPTION: ", ex);
        }
        return Optional.ofNullable(users);
    }

    @Override
    public void assignRole(Integer userId, Integer roleId) throws GymException {

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(ASSIGN_USER_ROLE_QUERY);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, roleId);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new GymException("ASSIGN ROLE FOR USER FAILED: ", ex);
        }
    }

    @Override
    public User add(User user) throws GymException {
        try (Connection connection = dataSource.getConnection()) {
            insertUser(user, connection);
        } catch (SQLException ex) {
            throw new GymException("ADD USER EXCEPTION: ", ex);
        }
        return user;
    }

    @Override
    public void addAll(List<User> users) throws GymException {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try {
                for (User user : users) {
                    insertUser(user, connection);
                }
                connection.commit();
            } catch (SQLException ex) {
                connection.rollback();
                throw new SQLException("CONNECTION ROLLBACK: ", ex);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            throw new GymException("ADD ALL USERS FAILED: ", ex);
        }
    }

    @Override
    public User update(User user) throws GymException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_QUERY)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getSurname());
            preparedStatement.setString(5, user.getPhone());
            preparedStatement.setInt(6, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new GymException("UPDATE USER EXCEPTION: ", ex);
        }
        return user;
    }

    @Override
    public void delete(Integer id) throws GymException {
        User user = null;
        deleteUserFromLinkedTableById(id);
        deleteUserFromTrainingById(id);
        try (Connection connection = dataSource.getConnection();

             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_QUERY)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new GymException("DELETE USER EXCEPTION: ", ex);
        }
    }

    private void deleteUserFromLinkedTableById(Integer userId) throws GymException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_FROM_USER_ROLE_QUERY)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new GymException("DELETE USER FROM USER ROLE EXCEPTION: ", ex);
        }
    }

    private void deleteUserFromTrainingById(Integer userId) throws GymException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_FROM_TRAINING_QUERY)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new GymException("DELETE USER FROM TRAINING EXCEPTION: ", ex);
        }
    }

    private User insertUser(User user, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getSurname());
            preparedStatement.setString(5, user.getPhone());

            final int effectiveRows = preparedStatement.executeUpdate();

            if (effectiveRows == 1) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getInt(ID_COLUMN));
                    }
                }
            }
        }
        return user;
    }

    private User getUser(ResultSet resultSet) throws SQLException, GymException {
        User user = new User();
        user.setId(resultSet.getInt(ID_COLUMN));
        user.setLogin(resultSet.getString(LOGIN_COLUMN));
        user.setPassword(resultSet.getString(PASSWORD_COLUMN));
        user.setName(resultSet.getString(NAME_COLUMN));
        user.setSurname(resultSet.getString(SURNAME_COLUMN));
        user.setPhone(resultSet.getString(PHONE_COLUMN));

        return user;
    }
}