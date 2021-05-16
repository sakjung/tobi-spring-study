package com.example.demo.dao;

import com.example.demo.domain.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private DataSource dataSource;

    public UserDao() {
    }

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void create() throws SQLException {
        Connection c = dataSource.getConnection();

        PreparedStatement ps1 = c.prepareStatement("DROP TABLE IF EXISTS users");
        PreparedStatement ps2 = c.prepareStatement("CREATE TABLE users (id varchar(10) primary key, name varchar(20) not null, password VARCHAR(10) not null)");

        ps1.executeUpdate();
        ps2.executeUpdate();

        ps1.close();
        ps2.close();
        c.close();
    }

    public void add(User user) throws SQLException {
        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement(
                "insert into users(id, name, password) values (?,?,?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public User get(String id) throws SQLException {
        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement("select * from users where id = ?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        rs.next();
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));

        rs.close();
        ps.close();
        c.close();

        return user;
    }

    public void deleteAll() throws SQLException {
        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement("delete from users");
        ps.executeUpdate();

        // 여기서 끊기면 close 못함

        ps.close();
        c.close();
    }

    public void setDataSource(final DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
