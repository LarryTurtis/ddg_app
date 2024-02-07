package com.ddg.restservice;

import java.util.UUID;

import lombok.Data;

@Data
public class SubmissionDTO {
    private UUID userId;
    private String comment;
    private Boolean isPositive;
}
