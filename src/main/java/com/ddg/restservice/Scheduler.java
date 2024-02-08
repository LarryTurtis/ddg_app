package com.ddg.restservice;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ddg.db.Entry;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Scheduler {

    private DBService dbService;
    private AsanaService asanaService;

    @Autowired
    public Scheduler(DBService dbService, AsanaService asanaService) {
        this.dbService = dbService;
        this.asanaService = asanaService;
    }

    @Scheduled(fixedRate = 10000)
    public void task() {
        Analysis analysis = gatherResults();
        log.info("Analysis: " + analysis.toString());
        this.asanaService.createTask(analysis);
        log.info("Task created successfully.");
    }

    private Analysis gatherResults() {
        List<Entry> entries = this.dbService.loadEntries();
        Integer numPositive = 0;
        Integer numNegative = 0;
        Float positivePercentage = 0.0f;
        Float negativePercentage = 0.0f;
        for (Iterator<Entry> iterator = entries.iterator(); iterator.hasNext();) {
            Entry entry = (Entry) iterator.next();
            if (entry.getIsPositive()) {
                numPositive++;
            } else {
                numNegative++;
            }
        }
        if (entries.size() > 0) {
            positivePercentage = (float) numPositive / entries.size();
            negativePercentage = (float) numNegative / entries.size();
        }
        return Analysis.builder()
                .numPositive(numPositive)
                .numNegative(numNegative)
                .positivePercentage(positivePercentage * 100f)
                .negativePercentage(negativePercentage * 100f)
                .build();
    }
}
