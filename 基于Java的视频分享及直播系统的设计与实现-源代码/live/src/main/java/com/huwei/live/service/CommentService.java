package com.huwei.live.service;

import com.github.pagehelper.PageInfo;
import com.huwei.live.pojo.Comment;
import com.huwei.live.request.CommentSearch;
import org.hibernate.validator.constraints.EAN;

import java.util.List;
import java.util.Map;

public interface CommentService {
    boolean add(Comment comment);

    boolean update(Comment comment);

    boolean delete(Integer commentId);

    PageInfo<Comment> queryByPage(CommentSearch commentSearch);

    PageInfo<Comment> queryBySearch(CommentSearch commentSearch);

    Map<String, List> queryUserAndVideoAndCommentById(Integer videoId);

    boolean agree(Integer commentId);

    boolean disagree(Integer commentId);
}
