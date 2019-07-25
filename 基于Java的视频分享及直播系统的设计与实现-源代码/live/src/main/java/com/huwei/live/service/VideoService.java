package com.huwei.live.service;

import com.github.pagehelper.PageInfo;
import com.huwei.live.pojo.Video;
import com.huwei.live.request.VideoSearch;

import java.util.List;

public interface VideoService {

    boolean add(Video video);

    boolean update(Video video);

    boolean delete(Integer videoId);

    PageInfo<Video> queryByPage(VideoSearch videoSearch);

    PageInfo<Video> selectBySearch(VideoSearch videoSearch);

    List<Video> recommend(String userName);

    List<Video> rand();

    List<Video> findByCategory(String category);

    Video getById(int videoId);
}
