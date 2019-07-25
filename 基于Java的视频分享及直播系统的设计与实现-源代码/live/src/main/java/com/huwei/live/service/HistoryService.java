package com.huwei.live.service;

import com.github.pagehelper.PageInfo;
import com.huwei.live.pojo.History;
import com.huwei.live.pojo.User;
import com.huwei.live.pojo.Video;
import com.huwei.live.request.HistorySearch;
import com.huwei.live.request.UserSearch;
import com.huwei.live.request.VideoSearch;

import java.util.List;

public interface HistoryService {

    boolean add(History history);

    boolean update(History history);

    boolean delete(Integer historyId);

    PageInfo<History> queryByPage(HistorySearch historySearch);

    PageInfo<Video> queryByUserName(HistorySearch historySearch);

    PageInfo<User> queryByVideoId(HistorySearch historySearch);
}
