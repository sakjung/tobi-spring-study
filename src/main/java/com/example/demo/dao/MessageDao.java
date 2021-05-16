package com.example.demo.dao;

import com.example.demo.connection.ConnectionMaker;

public class MessageDao {
    private final ConnectionMaker connectionMaker;

    public MessageDao(final ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }
}
