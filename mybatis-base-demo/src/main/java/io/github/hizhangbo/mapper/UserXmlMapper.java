package io.github.hizhangbo.mapper;

import io.github.hizhangbo.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Bob
 * @since 2021/2/21 1:49
 */
@Mapper
public interface UserXmlMapper {

    List<User> getAll();
    int insert(User user);
}
