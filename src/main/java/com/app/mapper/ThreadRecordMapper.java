package com.app.mapper;

import com.app.entity.ThreadRecord;
import org.springframework.stereotype.Repository;

@Repository
public interface ThreadRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ThreadRecord record);

    int insertSelective(ThreadRecord record);

    ThreadRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ThreadRecord record);

    int updateByPrimaryKey(ThreadRecord record);
}