package com.ssafy.trip.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.ssafy.trip.model.service.ReadCountService;

@Configuration
@EnableScheduling
public class BatchScheduler {

    @Autowired
    private ReadCountService readCountService;

    @Scheduled(cron = "20 28 09 * * ?")  // 매일 오후 2시 17분에 실행
    public void resetReadCountsJob() {
        readCountService.resetReadCounts();
    }
}
