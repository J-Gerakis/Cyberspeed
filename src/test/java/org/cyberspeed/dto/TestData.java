package org.cyberspeed.dto;

import com.google.gson.annotations.SerializedName;
import org.cyberspeed.dto.internal.Grid;
import org.cyberspeed.dto.output.Result;

public record TestData(
        @SerializedName("bet_amount")
        int betAmount,
        Grid grid,
        Result output
) {}
