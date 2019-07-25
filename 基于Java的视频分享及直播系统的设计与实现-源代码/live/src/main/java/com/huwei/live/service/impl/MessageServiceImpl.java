package com.huwei.live.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huwei.live.mapper.MessageMapper;
import com.huwei.live.mapper.UserMapper;
import com.huwei.live.pojo.Message;
import com.huwei.live.pojo.MessageExample;
import com.huwei.live.pojo.User;
import com.huwei.live.request.MessageSearch;
import com.huwei.live.service.MessageService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageServiceImpl implements MessageService {
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean add(Message message) {
        try {
            if(null != message){
                int result = messageMapper.insertSelective(message);
                if(result > 0){
                    return true;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return false;
    }

    @Override
    public boolean update(Message message) {
        try {
            int result = messageMapper.updateByPrimaryKeySelective(message);
            if(result > 0){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return false;
    }

    @Override
    public boolean delete(Integer messageId) {
        try {
            if(null != messageId){
                Message message = new Message();
                message.setId(messageId);
                message.setIsalive(0);
                int result = messageMapper.updateByPrimaryKeySelective(message);
                if(result>0){
                    return true;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return false;
    }

    @Override
    public PageInfo<Message> queryByPage(MessageSearch messageSearch) {
        try {
            if(null != messageSearch){
                if(null != messageSearch.getCurrentPage() && null != messageSearch.getPageSize()){
                    PageHelper.startPage(messageSearch.getCurrentPage(),messageSearch.getPageSize());
                    MessageExample messageExample = new MessageExample();
                    MessageExample.Criteria criteria = messageExample.createCriteria();
                    criteria.andIsaliveEqualTo(1);
                    List<Message> list = messageMapper.selectByExample(messageExample);
                    PageInfo<Message> pageInfo = new PageInfo<>(list);
                    return pageInfo;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return null;
    }

    @Override
    public Map<String,List> queryBySIdAndRId(Integer senderId, Integer receiverId) {
        try {
            Map<String,List> result = new HashMap<>();
            if(null != senderId && null != receiverId){
                List<Message> messages = messageMapper.selectBySIdAndRId(senderId,receiverId);
                List<User> senders = new ArrayList<>();
                List<User> receivers = new ArrayList<>();
                if(null != messages){
                    result.put("message",messages);
                    for (Message message : messages) {
                        int senderId2 = message.getSenderid();
                        senders.add(userMapper.selectByPrimaryKey(senderId2));
                        int receiverId2 = message.getReceiverid();
                        receivers.add(userMapper.selectByPrimaryKey(receiverId2));
                    }
                    result.put("sender",senders);
                    result.put("receiver",receivers);
                    return result;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return null;
    }
}
