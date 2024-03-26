package com.tianll.blog.model.mapper;

import com.tianll.blog.model.domain.User;

/**
* @author tianll
* @description 针对表【t_user】的数据库操作Mapper
* @createDate 2024-03-14 16:19:54
* @Entity com.tianll.blog.model.domain.User
*/
public interface UserMapper {

    int deleteByPrimaryKey(Long id);

    /**
     * user表插入数据
     * @param record user用户信息
     * @return 受影响行数
     */
    int insert(User record);

    /**
     * user表插入数据(信息不全则其他信息为默认值)
     * @param record 用户信息
     * @return  受影响行数
     */
    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    /**
     * 更新user表数据(user可以不全)
     * @param record 用户信息
     * @return 受影响行数
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * 根据客户id更新user表数据
     * @param record 用户信息
     * @return 受影响行数
     */
    int updateByPrimaryKey(User record);

}
