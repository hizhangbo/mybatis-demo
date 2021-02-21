package io.github.hizhangbo.controller;

import io.github.hizhangbo.entity.User;
import io.github.hizhangbo.mapper.UserAnnoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Bob
 * @since 2021/2/21 2:47
 */
@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserAnnoMapper userAnnoMapper;

    @GetMapping("all")
    public List<User> getAll() {
        return userAnnoMapper.getAll();
    }
}
