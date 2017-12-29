package com.huwei.provider.model;

import com.huwei.model.UserDTO;

/**
 * Created by huwei on 2017/6/15.
 */
public class User {
    private Integer id;
    private String username;

    public static User toUser(UserDTO user){
        User u = new User();
        u.setId(1);
        u.setUsername("xxx");
        return u;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
