package io.github.hizhangbo.dao;

import io.github.hizhangbo.model.LinkedInUser;
import io.github.hizhangbo.model.LinkedInUserExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LinkedInUserMapperTest {

    @Autowired
    private LinkedInUserMapper linkedInUserMapper;


    @Test
    public void selectByExample() {
        LinkedInUserExample example = new LinkedInUserExample();
        example.createCriteria().andIdLessThan(18);
        List<LinkedInUser> linkedInUsers = linkedInUserMapper.selectByExample(example);
        linkedInUsers.forEach(u -> System.out.println(u.getName()));
    }

    @Test
    public void selectByPrimaryKey() {
        LinkedInUser linkedInUser = linkedInUserMapper.selectByPrimaryKey(8);
        System.out.println(linkedInUser.getName());
    }
}