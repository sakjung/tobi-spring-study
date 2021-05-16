package com.example.demo.test;

import com.example.demo.connection.CountingConnectionMaker;
import com.example.demo.dao.CountingDaoFactory;
import com.example.demo.dao.UserDao;
import com.example.demo.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.sql.SQLException;

public class UserDaoConnectionCountingTest {
    @Test
    void connectionCountingTest() throws ClassNotFoundException, SQLException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CountingDaoFactory.class);
        UserDao userDao = context.getBean("userDao", UserDao.class);

        userDao.create();

        User user = new User();
        user.setId("whiteShip");
        user.setName("백기선");
        user.setPassword("married");

        userDao.add(user);

        System.out.println(user.getId() + "등록 성공");
        System.out.println();

        User userSaved = userDao.get(user.getId());
        System.out.println(userSaved.getName());
        System.out.println(userSaved.getPassword());
        System.out.println(userSaved.getId() + "조회 성공!");
        System.out.println();

        CountingConnectionMaker connectionMaker = context.getBean("connectionMaker", CountingConnectionMaker.class);
        System.out.println("### Connection Counter:" + connectionMaker.getCounter());
    }

    @Test
    void xmlTest() throws ClassNotFoundException, SQLException {
        ApplicationContext context = new GenericXmlApplicationContext("countingDaoContext.xml");
//        ApplicationContext context = new ClassPathXmlApplicationContext("daoContext.xml");
        UserDao userDao = context.getBean("userDao", UserDao.class);

        userDao.create();

        User user = new User();
        user.setId("whiteShip");
        user.setName("백기선");
        user.setPassword("married");

        userDao.add(user);

        System.out.println(user.getId() + "등록 성공");
        System.out.println();

        User userSaved = userDao.get(user.getId());
        System.out.println(userSaved.getName());
        System.out.println(userSaved.getPassword());
        System.out.println(userSaved.getId() + "조회 성공!");
        System.out.println();

        CountingConnectionMaker connectionMaker = context.getBean("connectionMaker", CountingConnectionMaker.class);
        System.out.println("### Connection Counter:" + connectionMaker.getCounter());
    }
}
