package com.ddg.restservice;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Scheduler {

    @Scheduled(fixedRate = 10000)
    public void gatherResults() {
        log.info("Hi mom");
    }
}
