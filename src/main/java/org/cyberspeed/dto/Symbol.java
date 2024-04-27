package org.cyberspeed.dto;

import com.google.common.base.Enums;
import com.google.common.base.Optional;
import com.google.gson.annotations.SerializedName;
import org.cyberspeed.exception.ScratchException;

public record Symbol (
    @SerializedName("reward_multiplier") Double rewardMultiplier,
    Integer extra,
    String type,
    String impact
) {
    public Symbol {
        // validation
        if(type == null || type.isEmpty()) {
            throw new ScratchException("symbol type cannot be null or empty");
        } else {
            type = type.toLowerCase();
            Optional<SYMBOL_TYPE> symbolType = Enums.getIfPresent(SYMBOL_TYPE.class, type);
            if(!symbolType.isPresent()) {
                throw new ScratchException("Invalid symbol type: " + type);
            }
            if(symbolType.get().equals(SYMBOL_TYPE.standard) && rewardMultiplier == null) {
                throw new ScratchException("reward_multiplier cannot be null for standard symbols");
            }
        }
        if(impact != null) {
            impact = impact.toLowerCase();
            Optional<SYMBOL_IMPACT> symbolImpact = Enums.getIfPresent(SYMBOL_IMPACT.class, impact);
            if(!symbolImpact.isPresent()) {
                throw new ScratchException("Invalid impact: " + impact);
            }
            if(symbolImpact.get().equals(SYMBOL_IMPACT.miss)) {
                extra = 0; //convenient to avoid doing some condition checks later on
            } else if(symbolImpact.get().equals(SYMBOL_IMPACT.multiply_reward) && rewardMultiplier == null) {
                throw new ScratchException("reward_multiplier cannot be null if impact is reward_multiplier");
            } else if(symbolImpact.get().equals(SYMBOL_IMPACT.extra_bonus) && extra == null) {
                throw new ScratchException("extra cannot be null if impact is extra_bonus");
            }
        }

    }

    public enum SYMBOL_TYPE {
        standard, bonus
    }

    public enum SYMBOL_IMPACT {
        multiply_reward, extra_bonus, miss
    }
}
