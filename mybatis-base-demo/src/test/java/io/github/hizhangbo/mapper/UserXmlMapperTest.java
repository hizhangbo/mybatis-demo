package io.github.hizhangbo.mapper;

import io.github.hizhangbo.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserXmlMapperTest {

    @Autowired
    private UserXmlMapper userXmlMapper;

    @Test
    public void getAll() {
        List<User> users = userXmlMapper.getAll();
        System.out.println(users);
    }

    /**
     * 测试 unique 索引新增失败
     * 新增返回主键，失败则为null
     */
    @Test
    public void insert() {
        User user = User.builder().name("Kobe").gender(User.Gender.male).build();
        userXmlMapper.insert(user);
        System.out.println("user id: " + user.getId());
    }
}