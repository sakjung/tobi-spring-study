package com.example.demo.dao;

public class AccountDao {
    private final ConnectionMaker connectionMaker;

    public AccountDao(final ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }
}
