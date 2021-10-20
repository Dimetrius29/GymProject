package com.itrex.java.lab.repository.impl;

import com.itrex.java.lab.entity.Coach;
import com.itrex.java.lab.entity.User;
import com.itrex.java.lab.entity.Role;
import com.itrex.java.lab.entity.UserRole;
import com.itrex.java.lab.repository.UserRoleRepository;
import com.itrex.java.lab.repository.UserRepository;
import com.itrex.java.lab.repository.RoleRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class JDBCUserRoleRepositoryImpl implements UserRoleRepository {

    private static final String USER_ID_COLUMN = "user_id";
    private static final String Role_ID_COLUMN = "role_id";

    private static final String SELECT_ALL_QUERY = "SELECT * FROM user_role";
    private static final String INSERT_USER_ROLE_QUERY = "INSERT INTO user_role(user_id, role_id) VALUES (?, ?)";
    private static final String UPDATE_USER_ROLE_QUERY = "UPDATE user_role SET user_id=?, role_id=?, WHERE user_id=? AND role_id=?";
    private static final String DELETE_USER_ROLE_QUERY = "DELETE FROM user_role WHERE user_id=?";

    private DataSource dataSource;

    public JDBCUserRoleRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<UserRole> selectAll() {
        List<UserRole> userRoles = new ArrayList<>();
        try (Connection connection = dataSource.getConnection(); Statement stm = connection.createStatement();
             ResultSet resultSet = stm.executeQuery(SELECT_ALL_QUERY)) {
            while (resultSet.next()) {
                UserRole userRole = new UserRole();
                UserRepository userRepository = new JDBCUserRepositoryImpl(dataSource);
                RoleRepository roleRepository = new JDBCRoleRepositoryImpl(dataSource);
                userRole.setUser(userRepository.selectById(resultSet.getInt(USER_ID_COLUMN)));
                userRole.setRole(roleRepository.selectById(resultSet.getInt(Role_ID_COLUMN)));

                userRoles.add(userRole);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return userRoles;
    }

    @Override
    public UserRole add(UserRole userRole) {
        try (Connection connection = dataSource.getConnection()) {
            insertUserRole(userRole, connection);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return userRole;
    }

    @Override
    public List<UserRole> addAll(List<UserRole> userRoles) {
        List<UserRole> addAllUserRole = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try {
                for (UserRole userRole : userRoles) {
                    addAllUserRole.add(insertUserRole(userRole, connection));
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
        return addAllUserRole;
    }

    @Override
    public UserRole update(UserRole userRole, User user, Role role) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_ROLE_QUERY)) {
            preparedStatement.setInt(1, userRole.getUser().getId());
            preparedStatement.setInt(2, userRole.getRole().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return userRole;
    }

    @Override
    public void delete(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_ROLE_QUERY)) {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private UserRole insertUserRole(UserRole userRole, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_ROLE_QUERY)) {
            preparedStatement.setInt(1, userRole.getUser().getId());
            preparedStatement.setInt(2, userRole.getRole().getId());
            preparedStatement.executeUpdate();
        }
        return userRole;
    }
}