package com.itrex.java.lab.repository.impl.jdbc;

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
import java.util.Optional;
import javax.sql.DataSource;

public class JDBCRoleRepositoryImpl implements RoleRepository {

    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";
    private static final String SELECT_ALL_ROLES_QUERY = "SELECT * FROM role";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM role WHERE id=";
    private static final String INSERT_ROLE_QUERY = "INSERT INTO role(name) VALUES (?)";
    private static final String UPDATE_ROLE_QUERY = "UPDATE role SET name=? WHERE id=?";

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
                Role role = getRole(resultSet);
                roles.add(role);
            }
        } catch (SQLException ex) {
            throw new GymException("SELECT ALL ROLES: ", ex);
        }
        return roles;
    }

    @Override
    public Optional<Role> selectById(Integer id) throws GymException {
        Role role = null;
        try (Connection connection = dataSource.getConnection();
             Statement stm = connection.createStatement();
             ResultSet resultSet = stm.executeQuery(SELECT_BY_ID_QUERY + id)) {
            if (resultSet.next()) {
                role = getRole(resultSet);
            }
        } catch (SQLException ex) {
            throw new GymException("SELECT ROLE BY ID: ", ex);
        }

        return Optional.ofNullable(role);
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

    private Role getRole(ResultSet resultSet) throws SQLException {
        Role role = new Role();
        role.setId(resultSet.getInt(ID_COLUMN));
        role.setName(resultSet.getString(NAME_COLUMN));

        return role;
    }
}