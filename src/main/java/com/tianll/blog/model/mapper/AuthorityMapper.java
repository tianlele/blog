package com.tianll.blog.model.mapper;

import com.tianll.blog.model.domain.Authority;

/**
* @author tianll
* @description 针对表【t_authority】的数据库操作Mapper
* @createDate 2024-03-14 16:19:54
* @Entity com.tianll.blog.model.domain.Authority
*/
public interface AuthorityMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Authority record);

    int insertSelective(Authority record);

    Authority selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Authority record);

    int updateByPrimaryKey(Authority record);

}
