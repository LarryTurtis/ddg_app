package com.ddg.restservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ddg.db.Entry;

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
        entry.setUserId(submissionDTO.getUserId());
        log.info("Converted to entry: " + entry.toString());
        dbService.store(entry);
        return new SubmissionResponse("Success");
    }
}
