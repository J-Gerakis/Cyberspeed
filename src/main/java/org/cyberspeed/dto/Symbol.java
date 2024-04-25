package org.cyberspeed.dto;

public record Symbol (
    String symbolName,
    Double rewardMultiplier,
    Integer extra,
    String type,
    String impact
) {}
