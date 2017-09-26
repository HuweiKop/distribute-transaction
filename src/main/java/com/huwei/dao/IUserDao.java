package com.huwei.dao;

import com.huwei.model.User;
import org.apache.ibatis.annotations.Delete;

/**
 * Created by huwei on 2017/6/15.
 */
public interface IUserDao {
    int insert(User user);

    @Delete("delete from user where id=#{id}")
    int delete(long id);

    int update(User user);
}
