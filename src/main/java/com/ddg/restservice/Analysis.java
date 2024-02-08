package com.ddg.restservice;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Analysis {
    private Integer numPositive;
    private Integer numNegative;
    private Float positivePercentage;
    private Float negativePercentage;
}
