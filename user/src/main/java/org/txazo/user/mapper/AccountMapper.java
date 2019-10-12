package org.txazo.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.txazo.user.entity.Account;

@Mapper
public interface AccountMapper {

    int insertAccount(Account account);

    Account getAccount(int userId, int source);

}
