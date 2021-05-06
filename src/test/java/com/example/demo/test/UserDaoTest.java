package com.example.demo.test;

import com.example.demo.dao.DaoFactory;
import com.example.demo.dao.UserDao;
import com.example.demo.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.sql.SQLException;

public class UserDaoTest {
    @Test
    void connectionCountingTest() throws ClassNotFoundException, SQLException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
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
    }

    @Test
    void daoFactoryVersusApplicationContext() {
        DaoFactory daoFactory = new DaoFactory();
        System.out.println("##### DAOFACTORY");
        System.out.println(daoFactory.userDao());
        System.out.println(daoFactory.userDao());
        System.out.println();

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        System.out.println("##### APPLICATION CONTEXT");
        System.out.println(context.getBean("userDao", UserDao.class));
        System.out.println(context.getBean("userDao", UserDao.class));
    }

    @Test
    void xmlTest() throws ClassNotFoundException, SQLException {
        ApplicationContext context = new GenericXmlApplicationContext("daoContext.xml");
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
    }
}
