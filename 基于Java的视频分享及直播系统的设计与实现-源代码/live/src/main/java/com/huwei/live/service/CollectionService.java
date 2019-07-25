package com.huwei.live.service;

import com.github.pagehelper.PageInfo;
import com.huwei.live.pojo.Collection;
import com.huwei.live.pojo.Video;
import com.huwei.live.request.CollectionSearch;
import com.huwei.live.request.VideoSearch;

import java.util.List;

public interface CollectionService {

    boolean add(Collection collection);

    boolean update(Collection collection);

    boolean delete(Integer collectionId);

    PageInfo<Collection> queryByPage(CollectionSearch collectionSearch);

    PageInfo<Video> queryByUserId(CollectionSearch collectionSearch);

}
