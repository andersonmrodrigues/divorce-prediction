package com.unisc.trab1iaa.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConfigDTO {

    private Double learningRate;
    private Double errorRate;
    private Integer maxInterationNumber;
}
