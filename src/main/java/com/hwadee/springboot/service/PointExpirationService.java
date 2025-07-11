package com.hwadee.springboot.service;

import com.hwadee.springboot.service.PointRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PointExpirationService {
    
    @Autowired
    private PointRecordService pointRecordService;
    
    /**
     * 定时任务：每天凌晨1点执行积分过期检查
     * 将过期的积分标记为已过期
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void processExpiredPoints() {
        pointRecordService.processExpiredPoints();
    }
}