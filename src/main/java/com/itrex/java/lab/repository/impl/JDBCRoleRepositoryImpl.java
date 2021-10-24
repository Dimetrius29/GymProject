package com.itrex.java.lab.repository.impl;

import com.itrex.java.lab.entity.Role;
import com.itrex.java.lab.exception.GymException;
import com.itrex.java.lab.repository.RoleRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class JDBCRoleRepositoryImpl implements RoleRepository {

    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";
    private static final String SELECT_ALL_ROLES_QUERY = "SELECT * FROM role";
    private static final String SELECT_ALL_ROLES_BY_USER_QUERY = "SELECT * FROM role WHERE id IN (SELECT role_id FROM user_role WHERE user_id=?)";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM role WHERE id=";
    private static final String INSERT_ROLE_QUERY = "INSERT INTO role(name) VALUES (?)";
    private static final String UPDATE_ROLE_QUERY = "UPDATE role SET name=? WHERE id=?";
    private static final String DELETE_ROLE_QUERY = "DELETE FROM role WHERE id=?";
    private static final String DELETE_ROLE_FROM_USER_ROLE_QUERY = "DELETE FROM user_role WHERE role_id=?";

    private DataSource dataSource;

    public JDBCRoleRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Role> selectAll() throws GymException {
        List<Role> roles = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement stm = connection.createStatement();
             ResultSet resultSet = stm.executeQuery(SELECT_ALL_ROLES_QUERY)) {
            while (resultSet.next()) {
                Role role = new Role();
                role.setId(resultSet.getInt(ID_COLUMN));
                role.setName(resultSet.getString(NAME_COLUMN));
                roles.add(role);
            }
        } catch (SQLException ex) {
            throw new GymException("SELECT ALL ROLES: ", ex);
        }
        return roles;
    }

    @Override
    public List<Role> getAllUserRoles(Integer userId) throws GymException {
        List<Role> roles = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ROLES_BY_USER_QUERY)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            {
                while (resultSet.next()) {
                    Role role = new Role();
                    role.setId(resultSet.getInt(ID_COLUMN));
                    role.setName(resultSet.getString(NAME_COLUMN));
                    roles.add(role);
                }
            }
        } catch (SQLException ex) {
            throw new GymException("GET ALL USER BY ROLE EXCEPTION: ", ex);
        }
        return roles;
    }

    @Override
    public Role selectById(Integer id) throws GymException {
        Role role = new Role();
        try (Connection connection = dataSource.getConnection();
             Statement stm = connection.createStatement();
             ResultSet resultSet = stm.executeQuery(SELECT_BY_ID_QUERY + id)) {
            if (resultSet.next()) {
                role.setId(id);
                role.setName(resultSet.getString(NAME_COLUMN));
            } else {
                throw new NotFoundEx();
            }
        } catch (SQLException | NotFoundEx ex) {
            throw new GymException("SELECT ROLE BY ID: ", ex);
        }
        return role;
    }

    @Override
    public Role add(Role role) throws GymException {
        try (Connection connection = dataSource.getConnection()) {
            insertRole(role, connection);
        } catch (SQLException ex) {
            throw new GymException("ADD ROLE: ", ex);
        }
        return role;
    }

    @Override
    public Role update(Role role) throws GymException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ROLE_QUERY)) {
            preparedStatement.setString(1, role.getName());
            preparedStatement.setInt(2, role.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new GymException("UPDATE ROLE EXCEPTION: ", ex);
        }
        return role;
    }

    @Override
    public void deleteRole(Integer id) throws GymException {
        deleteRoleFromLinkedTableById(id);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ROLE_QUERY)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new GymException("DELETE ROLE EXCEPTION: ", ex);
        }
    }

    @Override
    public void deleteRoleFromLinkedTableById(Integer userId) throws GymException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ROLE_FROM_USER_ROLE_QUERY)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new GymException("DELETE USER FROM USER ROLE EXCEPTION: ", ex);
        }
    }

    private Role insertRole(Role role, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ROLE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, role.getName());

            final int effectiveRows = preparedStatement.executeUpdate();

            if (effectiveRows == 1) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        role.setId(generatedKeys.getInt(ID_COLUMN));
                    }
                }
            }
        }
        return role;
    }

    public class NotFoundEx extends Exception {
    }
}