package com.example.demo.dao;

import com.example.demo.connection.ConnectionMaker;

public class AccountDao {
    private final ConnectionMaker connectionMaker;

    public AccountDao(final ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }
}
