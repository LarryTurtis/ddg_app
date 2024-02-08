package com.ddg.restservice;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import com.asana.Client;
import com.asana.models.Task;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AsanaService {

    private String ASANA_PROJECT = System.getenv("ASANA_PROJECT");
    private String ASANA_WORKSPACE_ID = System.getenv("ASANA_WORKSPACE");
    private String ASANA_USER_ID = System.getenv("ASANA_USER_ID");
    private String ASANA_PAT = System.getenv("ASANA_PAT");

    public void createTask(Analysis analysis) {

        try {
            Client client = Client.accessToken(ASANA_PAT);
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            Task result = client.tasks.createInWorkspace(ASANA_WORKSPACE_ID)
                    .data("name", date)
                    .data("due_on", date)
                    .data("assignee", ASANA_USER_ID)
                    .data("html_notes", formatAnalysis(analysis))
                    .data("projects", Arrays.asList(ASANA_PROJECT))
                    .option("pretty", true)
                    .execute();
            log.info("Task created: " + result.gid);
        } catch (IOException e) {
            log.error("Error creating Asana task", e);
            throw new DDGException("Error creating Asana task");
        }
    }

    private String formatAnalysis(Analysis analysis) {
        return "<body><strong>😀 Positive: </strong>" + analysis.getNumPositive() +
                " (" + analysis.getPositivePercentage() + "%)\n" +
                "<strong>😢 Negative: </strong>" + analysis.getNumNegative() +
                " (" + analysis.getNegativePercentage() + "%)\n" +
                "</body>";
    }
}
