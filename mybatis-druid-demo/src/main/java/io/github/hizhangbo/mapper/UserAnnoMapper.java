package io.github.hizhangbo.mapper;

import io.github.hizhangbo.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Bob
 * @since 2021/2/20 21:26
 */
@Mapper
public interface UserAnnoMapper {

    @Select("SELECT * FROM tmp_user")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "gender", column = "gender", javaType = User.Gender.class)
    })
    List<User> getAll();

    @Insert("INSERT INTO tmp_user(name,gender) VALUES(#{name},#{gender})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);
}
