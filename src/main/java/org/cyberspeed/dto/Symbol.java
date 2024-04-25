package org.cyberspeed.dto;

import com.google.common.base.Enums;
import com.google.gson.annotations.SerializedName;

public record Symbol (
    @SerializedName("reward_multiplier") Double rewardMultiplier,
    Integer extra,
    String type,
    String impact
) {
    public Symbol {
        // validation
        if(type == null || type.isEmpty()) {
            throw new IllegalArgumentException("symbol type cannot be null or empty");
        } else {
            type = type.toLowerCase();
            if(!Enums.getIfPresent(SYMBOL_TYPE.class, type).isPresent()) {
                throw new IllegalArgumentException("Invalid symbol type: " + type);
            };
            if(type.equals(SYMBOL_TYPE.standard.name()) && rewardMultiplier == null) {
                throw new IllegalArgumentException("reward_multiplier cannot be null for standard symbols");
            }
        }
        if(impact != null) {
            impact = impact.toLowerCase();
            if(!Enums.getIfPresent(SYMBOL_IMPACT.class, impact).isPresent()) {
                throw new IllegalArgumentException("Invalid impact: " + impact);
            }
            if(impact.equals(SYMBOL_IMPACT.miss.name())) {
                extra = 0; //convenient to avoid doing some condition checks later on
            } else if(impact.equals(SYMBOL_IMPACT.multiply_reward.name()) && rewardMultiplier == null) {
                throw new IllegalArgumentException("reward_multiplier cannot be null if impact is reward_multiplier");
            } else if(type.equals(SYMBOL_IMPACT.extra_bonus.name()) && extra == null) {
                throw new IllegalArgumentException("extra cannot be null if impact is extra_bonus");
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
