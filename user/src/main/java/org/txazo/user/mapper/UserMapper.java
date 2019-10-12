package org.txazo.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.txazo.user.entity.User;

@Mapper
public interface UserMapper {

    int insertUser(User user);

    User getUser(int userId);

}
