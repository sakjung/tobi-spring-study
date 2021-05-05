package com.example.demo.dao;

public class MessageDao {
    private final ConnectionMaker connectionMaker;

    public MessageDao(final ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }
}
