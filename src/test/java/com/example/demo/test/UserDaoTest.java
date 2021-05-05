package com.example.demo.test;

import com.example.demo.dao.DaoFactory;
import com.example.demo.dao.UserDao;
import com.example.demo.domain.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class UserDaoTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao userDao = context.getBean("userDao", UserDao.class);

        userDao.create();

        User user = new User();
        user.setId("whiteShip");
        user.setName("백기선");
        user.setPassword("married");

        userDao.add(user);

        System.out.println(user.getId() + "등록 성공");

        User userSaved = userDao.get(user.getId());
        System.out.println(userSaved.getName());
        System.out.println(userSaved.getPassword());
        System.out.println(userSaved.getId() + "조회 성공!");
    }
}
