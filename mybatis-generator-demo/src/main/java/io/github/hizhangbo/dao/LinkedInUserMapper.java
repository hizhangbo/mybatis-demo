package io.github.hizhangbo.dao;

import io.github.hizhangbo.model.LinkedInUser;
import io.github.hizhangbo.model.LinkedInUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LinkedInUserMapper {
    long countByExample(LinkedInUserExample example);

    int deleteByExample(LinkedInUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LinkedInUser record);

    int insertSelective(LinkedInUser record);

    List<LinkedInUser> selectByExample(LinkedInUserExample example);

    LinkedInUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LinkedInUser record, @Param("example") LinkedInUserExample example);

    int updateByExample(@Param("record") LinkedInUser record, @Param("example") LinkedInUserExample example);

    int updateByPrimaryKeySelective(LinkedInUser record);

    int updateByPrimaryKey(LinkedInUser record);
}