package com.tianll.blog.model.mapper;

import com.tianll.blog.model.domain.UserAuthority;

/**
* @author tianll
* @description 针对表【t_user_authority】的数据库操作Mapper
* @createDate 2024-03-14 16:19:54
* @Entity com.tianll.blog.model.domain.UserAuthority
*/
public interface UserAuthorityMapper {

    int deleteByPrimaryKey(Long id);

    int insert(UserAuthority record);

    int insertSelective(UserAuthority record);

    UserAuthority selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserAuthority record);

    int updateByPrimaryKey(UserAuthority record);

}
