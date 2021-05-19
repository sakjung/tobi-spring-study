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
        // 나는 로컬 클래스
        class AddStatement implements StatementStrategy {
            User user;

            public AddStatement(final User user) {
                this.user = user;
            }

            @Override
            public PreparedStatement makePreparedStatement(final Connection c) throws SQLException {
                PreparedStatement ps = c.prepareStatement("INSERT INTO users(id, name, password) values(?, ?, ?)");
                ps.setString(1, user.getId());
                ps.setString(2, user.getName());
                ps.setString(3, user.getPassword());

                return ps;
            }
        }

        StatementStrategy st = new AddStatement(user);
        jdbcContextWithStatementStrategy(st);
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

    public int getCount() throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            c = dataSource.getConnection();
            ps = c.prepareStatement("SELECT count(*) FROM users");
            ps.executeUpdate();

            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            System.out.println("EXCEPTION");
            throw e;
        } finally {
            // close 는 만들어준 순서 (c - ps - rs) 의 반대로 하는 것이 원칙
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println("resultSet close failed");
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    System.out.println("preparedStatement close failed");
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    System.out.println("connection close failed");
                }
            }
        }
    }

    // 나는 클라이언트
    public void deleteAll() throws SQLException {
        StatementStrategy st = new DeleteAllStatement();
        jdbcContextWithStatementStrategy(st); // st를 DI!
    }

    public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;

        try {
            c = dataSource.getConnection();

            ps = stmt.makePreparedStatement(c);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("EXCEPTION");
        } finally {
            if (ps != null) {
                try { // 밑에 있는 c.close를 위해 try catch 처리 해줌
                    ps.close();
                } catch (SQLException e) {
                    System.out.println("preparedStatement close failed");
                }
            }
            if (c != null) {
                try { // 얘는 그냥 함
                    c.close();
                } catch (SQLException e) {
                    System.out.println("connection close failed");
                }
            }
        }
    }

//    protected abstract PreparedStatement makeStatement(final Connection c) throws SQLException;

    public void setDataSource(final DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
