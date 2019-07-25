package com.huwei.live.service;

import com.github.pagehelper.PageInfo;
import com.huwei.live.pojo.Message;
import com.huwei.live.request.MessageSearch;

import java.util.List;
import java.util.Map;

public interface MessageService {

    boolean add(Message message);

    boolean update(Message message);

    boolean delete(Integer messageId);

    PageInfo<Message> queryByPage(MessageSearch messageSearch);

    Map<String,List> queryBySIdAndRId(Integer senderId, Integer receiverId);
}
