package com.huwei.live.mapper;

import com.huwei.live.pojo.Collection;
import com.huwei.live.pojo.CollectionExample;
import java.util.List;

import com.huwei.live.request.CollectionSearch;
import org.apache.ibatis.annotations.Param;

public interface CollectionMapper {
    int countByExample(CollectionExample example);

    int deleteByExample(CollectionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Collection record);

    int insertSelective(Collection record);

    List<Collection> selectByExample(CollectionExample example);

    Collection selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Collection record, @Param("example") CollectionExample example);

    int updateByExample(@Param("record") Collection record, @Param("example") CollectionExample example);

    int updateByPrimaryKeySelective(Collection record);

    int updateByPrimaryKey(Collection record);

    List<Collection> selectBySearch(CollectionSearch collectionSearch);
}