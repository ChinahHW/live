package com.huwei.live.mapper;

import com.huwei.live.pojo.History;
import com.huwei.live.pojo.HistoryExample;
import java.util.List;

import com.huwei.live.request.HistorySearch;
import org.apache.ibatis.annotations.Param;
import sun.security.ssl.HandshakeInStream;

public interface HistoryMapper {
    int countByExample(HistoryExample example);

    int deleteByExample(HistoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(History record);

    int insertSelective(History record);

    List<History> selectByExample(HistoryExample example);

    History selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") History record, @Param("example") HistoryExample example);

    int updateByExample(@Param("record") History record, @Param("example") HistoryExample example);

    int updateByPrimaryKeySelective(History record);

    int updateByPrimaryKey(History record);

    List<History> selectBySearch(HistorySearch historySearch);
}