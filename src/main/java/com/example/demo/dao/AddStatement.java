package com.example.demo.dao;

import com.example.demo.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddStatement implements StatementStrategy {
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
