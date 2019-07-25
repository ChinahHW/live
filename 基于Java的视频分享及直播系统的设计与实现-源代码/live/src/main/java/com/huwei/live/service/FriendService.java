package com.huwei.live.service;

import com.github.pagehelper.PageInfo;
import com.huwei.live.pojo.Friend;
import com.huwei.live.pojo.User;
import com.huwei.live.request.FriendSearch;

import java.util.List;

public interface FriendService {

    boolean add(Friend friend);

    boolean update(Friend friend);

    boolean delete(Integer friendId);

    PageInfo<Friend> queryByPage(FriendSearch friendSearch);

    PageInfo<User> selectByUserName(FriendSearch friendSearch);

    List<Friend> isFriend(String userName, Integer friendId);
}
