package com.huwei.live.mapper;

import com.huwei.live.pojo.Video;
import com.huwei.live.pojo.VideoExample;
import java.util.List;

import com.huwei.live.request.VideoSearch;
import org.apache.ibatis.annotations.Param;

public interface VideoMapper {
    int countByExample(VideoExample example);

    int deleteByExample(VideoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Video record);

    int insertSelective(Video record);

    List<Video> selectByExample(VideoExample example);

    Video selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Video record, @Param("example") VideoExample example);

    int updateByExample(@Param("record") Video record, @Param("example") VideoExample example);

    int updateByPrimaryKeySelective(Video record);

    int updateByPrimaryKey(Video record);

    List<Video> selectBySearch(VideoSearch videoSearch);

    List<Integer> getRecomment(@Param("videoIds") List<Integer> videoIds,@Param("size") int size,@Param("userId") int userId);

    List<Video> selectRand();
}