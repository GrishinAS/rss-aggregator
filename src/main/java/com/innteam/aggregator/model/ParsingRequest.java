package com.innteam.aggregator.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParsingRequest {
    private String address;
    private String rule;
}
