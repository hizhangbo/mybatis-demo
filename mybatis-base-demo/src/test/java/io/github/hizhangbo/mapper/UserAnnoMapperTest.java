package io.github.hizhangbo.mapper;

import io.github.hizhangbo.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserAnnoMapperTest {

    @Autowired
    private UserAnnoMapper userAnnoMapper;

    @Test
    public void getAll() {
        List<User> users = userAnnoMapper.getAll();
        System.out.println(users);
    }

    @Test
    public void insert() {
        userAnnoMapper.insert(User.builder()
                .name("James")
                .gender(User.Gender.male)
                .build());
    }
}