package com.app.service.impl;

import com.app.entity.Test;
import com.app.mapper.TestMapper;
import com.app.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private TestMapper testMapper;
    @Override
    public Integer add(Test test) {
        return testMapper.insert(test);
    }
}
