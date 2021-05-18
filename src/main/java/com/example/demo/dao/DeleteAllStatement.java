package com.example.demo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteAllStatement implements StatementStrategy {
    @Override
    public PreparedStatement makePreparedStatement(final Connection c) throws SQLException {
        PreparedStatement ps = c.prepareStatement("DELETE from users");
        return ps;
    }
}
