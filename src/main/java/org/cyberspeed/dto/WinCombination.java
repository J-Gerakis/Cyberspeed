package org.cyberspeed.dto;

import com.google.common.base.Enums;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.gson.annotations.SerializedName;
import org.cyberspeed.exception.ScratchException;

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
                        throw new ScratchException("reward_multiplier cannot be null");
                }
                Optional<WIN_GROUP> winGroup;
                Optional<WIN_CONDITION> winCondition;
                if(Strings.isNullOrEmpty(group)) {
                        throw new ScratchException("group cannot be null");
                } else {
                        group = group.toLowerCase();
                        winGroup = Enums.getIfPresent(WIN_GROUP.class, group);
                        if(!winGroup.isPresent()) {
                                throw new ScratchException("Invalid group: " + group);
                        }
                }

                if(Strings.isNullOrEmpty(when)) {
                        throw new ScratchException("when cannot be null");
                } else {
                        when = when.toLowerCase();
                        winCondition = Enums.getIfPresent(WIN_CONDITION.class, when);
                        if(!winCondition.isPresent()) {
                                throw new ScratchException("Invalid when condition: " + when);
                        } else if(winCondition.get().equals(WIN_CONDITION.same_symbols)) {
                                if(count == null) {
                                        throw new ScratchException("count cannot be null for same_symbols win condition");
                                }
                                if(count < 1) {
                                        throw new ScratchException("count cannot be less than 1 for same_symbols win condition");
                                }
                                if(!winGroup.get().equals(WIN_GROUP.same_symbols)) {
                                        throw new ScratchException("Invalid group: " + group + " for same_symbols win condition");
                                }
                        } else if(winCondition.get().equals(WIN_CONDITION.linear_symbols)) {
                                if(winGroup.get().equals(WIN_GROUP.same_symbols)) {
                                        throw new ScratchException("Invalid group: " + group + " for linear_symbols win condition");
                                }
//                                if(coveredArea == null) {
//                                        throw new ScratchException("covered_areas cannot be null for linear_symbols win condition");
//                                }
                                //note: covered_area unused
                        }
                }
                if(count == null) {
                        count = 0;
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
