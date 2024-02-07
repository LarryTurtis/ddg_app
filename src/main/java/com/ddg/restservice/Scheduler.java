package com.ddg.restservice;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableScheduling
public class Scheduler {

    @Scheduled(fixedDelay = 2000)
    public void gatherResults() {
        log.info("Hi mom");
    }
}
