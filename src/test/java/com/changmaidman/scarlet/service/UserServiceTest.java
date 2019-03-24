package com.changmaidman.scarlet.service;

import com.changmaidman.scarlet.config.UserConfig;
import com.changmaidman.scarlet.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {UserService.class, UserConfig.class})
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testToGetAllUsersInDatabase() {

        List<User> users = userService.getAllUser();
        assertEquals(users.size(), 3);
    }
}