package com.huwei.live.mapper;

import com.huwei.live.pojo.Friend;
import com.huwei.live.pojo.FriendExample;
import java.util.List;

import com.huwei.live.pojo.User;
import com.huwei.live.request.FriendSearch;
import org.apache.ibatis.annotations.Param;

public interface FriendMapper {
    int countByExample(FriendExample example);

    int deleteByExample(FriendExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Friend record);

    int insertSelective(Friend record);

    List<Friend> selectByExample(FriendExample example);

    Friend selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Friend record, @Param("example") FriendExample example);

    int updateByExample(@Param("record") Friend record, @Param("example") FriendExample example);

    int updateByPrimaryKeySelective(Friend record);

    int updateByPrimaryKey(Friend record);

    List<Friend> selectBySearch(FriendSearch friendSearch);
}