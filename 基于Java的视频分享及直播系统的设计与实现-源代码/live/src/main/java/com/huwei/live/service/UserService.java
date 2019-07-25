package com.huwei.live.service;

import com.github.pagehelper.PageInfo;
import com.huwei.live.pojo.User;
import com.huwei.live.request.UserSearch;

public interface UserService {

    User login(String userAccount, String password);

    boolean add(User user);

    boolean update(User user);

    boolean delete(Integer userId);

    PageInfo<User> queryByPage(UserSearch userSearch);

    User selectById(Integer userId);
}
