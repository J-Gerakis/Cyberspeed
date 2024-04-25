package org.cyberspeed.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public record ProbabilityBonus(
        @SerializedName("symbols") Map<String, Integer> valuesByBonus
) {}
