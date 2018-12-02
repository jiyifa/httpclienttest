package com.app.service.impl;

import com.app.mapper.ThreadRecordMapper;
import com.app.service.ThreadRecordService;
import com.app.util.ThreadPoolHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThreadRecordServiceImpl implements ThreadRecordService {
    @Autowired
    private ThreadRecordMapper threadRecordMapper;

    @Override
    public void record() {
        String urls[] = {"http://www.baidu.com","http://www.baidu.com","http://www.baidu.com","http://www.baidu.com","http://www.baidu.com","http://www.baidu.com","http://www.baidu.com","http://www.baidu.com","http://www.baidu.com","http://www.baidu.com"};
        ThreadPoolHttpClient threadPoolHttpClient = new ThreadPoolHttpClient(threadRecordMapper,urls);
        try {
            threadPoolHttpClient.test();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
