package com.huwei.provider.dao;

import com.huwei.provider.model.User;
import org.apache.ibatis.annotations.Delete;
import org.springframework.stereotype.Repository;

/**
 * Created by huwei on 2017/6/15.
 */
@Repository
public interface IUserDao {
    int insert(User user);

    @Delete("delete from user where id=#{id}")
    int delete(long id);

    int update(User user);
}
