package com.ddg.restservice.controllers;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ddg.models.Entry;
import com.ddg.models.SubmissionDTO;
import com.ddg.models.SubmissionResponse;
import com.ddg.restservice.services.DBService;

@RestController
@Slf4j
public class Controller {

    private DBService dbService;

    @Autowired
    public Controller(DBService dbService) {
        this.dbService = dbService;
    }

    @CrossOrigin
    @GetMapping("/ping")
    public String ping() {
        return "Hello!";
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping("/submit")
    public SubmissionResponse submit(@RequestBody SubmissionDTO submissionDTO) {
        log.info("Received submission: " + submissionDTO.toString());
        Entry entry = new Entry();
        entry.setComment(submissionDTO.getComment());
        entry.setIsPositive(submissionDTO.getIsPositive());
        entry.setUserId(UUID.randomUUID());
        log.info("Converted to entry: " + entry.toString());
        dbService.store(entry);
        return new SubmissionResponse("Success");
    }
}
