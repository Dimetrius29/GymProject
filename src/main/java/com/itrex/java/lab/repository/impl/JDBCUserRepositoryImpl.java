package com.itrex.java.lab.repository.impl;

import com.itrex.java.lab.entity.User;
import com.itrex.java.lab.repository.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class JDBCUserRepositoryImpl implements UserRepository {

    private static final String ID_COLUMN = "id";
    private static final String LOGIN_COLUMN = "login";
    private static final String PASSWORD_COLUMN = "password";
    private static final String NAME_COLUMN = "name";
    private static final String SURNAME_COLUMN = "surname";
    private static final String PHONE_COLUMN = "phone";

    private static final String SELECT_ALL_QUERY = "SELECT * FROM user";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM user WHERE id=";
    private static final String INSERT_USER_QUERY = "INSERT INTO user(login, password, name, surname, phone) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_USER_QUERY = "UPDATE user SET login=?, password=?, name=?, surname=?, phone=? WHERE id=?";
    private static final String DELETE_USER_QUERY = "DELETE FROM user WHERE id=?";

    private DataSource dataSource;

    public JDBCUserRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<User> selectAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement stm = connection.createStatement();
             ResultSet resultSet = stm.executeQuery(SELECT_ALL_QUERY)) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt(ID_COLUMN));
                user.setLogin(resultSet.getString(LOGIN_COLUMN));
                user.setPassword(resultSet.getString(PASSWORD_COLUMN));
                user.setName(resultSet.getString(NAME_COLUMN));
                user.setSurname(resultSet.getString(SURNAME_COLUMN));
                user.setPhone(resultSet.getString(PHONE_COLUMN));

                users.add(user);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return users;
    }

    @Override
    public User selectById(Integer id) {
        User user = new User();
        try (Connection connection = dataSource.getConnection();
             Statement stm = connection.createStatement();
             ResultSet resultSet = stm.executeQuery(SELECT_BY_ID_QUERY + id)) {
            if (resultSet.next()) {
                user.setId(id);
                user.setLogin(resultSet.getString(LOGIN_COLUMN));
                user.setPassword(resultSet.getString(PASSWORD_COLUMN));
                user.setName(resultSet.getString(NAME_COLUMN));
                user.setSurname(resultSet.getString(SURNAME_COLUMN));
                user.setPhone(resultSet.getString(PHONE_COLUMN));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return user;
    }

    @Override
    public User add(User user) {
        try (Connection connection = dataSource.getConnection()) {
            insertUser(user, connection);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return user;
    }

    @Override
    public void addAll(List<User> users) {
        List<User> addAllUser = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try {
                for (User user : users) {
                    insertUser(user, connection);
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
    }

    @Override
    public User update(User user) {
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
            ex.printStackTrace();
        }
        return user;
    }

    @Override
    public void delete(Integer id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_QUERY)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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
}