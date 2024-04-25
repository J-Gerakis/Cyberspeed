package org.cyberspeed.dto;

import com.google.common.base.Enums;
import com.google.gson.annotations.SerializedName;

public record WinCombination(
        @SerializedName("reward_multiplier") Double rewardMultiplier,
        String when,
        Integer count,
        String group,
        @SerializedName("covered_areas") String[][] coveredArea
) {
        public WinCombination {
                //validation
                if(rewardMultiplier == null) {
                        throw new IllegalArgumentException("reward_multiplier cannot be null");
                }
                if(group == null) {
                        throw new IllegalArgumentException("group cannot be null");
                } else {
                        if(!Enums.getIfPresent(WIN_GROUP.class, group).isPresent()) {
                                throw new IllegalArgumentException("Invalid group: " + group);
                        }
                }
                if(when == null) {
                        throw new IllegalArgumentException("when cannot be null");
                } else {
                        if(!Enums.getIfPresent(WIN_CONDITION.class, when).isPresent()) {
                                throw new IllegalArgumentException("Invalid when condition: " + when);
                        }
                        if(when.equals(WIN_CONDITION.same_symbols.name())) {
                                if(count == null) {
                                        throw new IllegalArgumentException("count cannot be null for same_symbols win condition");
                                }
                                if(!group.equals(WIN_GROUP.same_symbols.name())) {
                                        throw new IllegalArgumentException("Invalid group: " + group + " for same_symbols win condition");
                                }
                        } else if(when.equals(WIN_CONDITION.linear_symbols.name())) {
                                if(group.equals(WIN_GROUP.same_symbols.name())) {
                                        throw new IllegalArgumentException("Invalid group: " + group + " for linear_symbols win condition");
                                }
                                if(coveredArea == null) {
                                        throw new IllegalArgumentException("covered_areas cannot be null for linear_symbols win condition");
                                }
                                //note: add area size check
                        }
                }

        }

        public enum WIN_GROUP {
                same_symbols, horizontally_linear_symbols, vertically_linear_symbols,
                ltr_diagonally_linear_symbols, rtl_diagonally_linear_symbols
        }

        public enum WIN_CONDITION {
                linear_symbols, same_symbols
        }
}
