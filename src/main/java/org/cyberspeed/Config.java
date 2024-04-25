package org.cyberspeed;

import com.google.gson.JsonObject;
import org.cyberspeed.dto.ConfigFile;
import org.cyberspeed.dto.Symbol;


import java.util.ArrayList;
import java.util.List;

public record Config(
        Integer rows,
        Integer columns,
        List<Symbol> symbols
) {

    public static Config map(ConfigFile configDTO) {

        List<Symbol> symbols = new ArrayList<>();
        JsonObject symbolsDtoMap = configDTO.symbols().getAsJsonObject();

        for (String key : symbolsDtoMap.keySet()) {
            JsonObject symbolsParamsMap = symbolsDtoMap.getAsJsonObject(key);
            symbols.add(new Symbol(
                    key,
                    (double) getOrElse(symbolsParamsMap, "reward_multiplier", 1.0),
                    (int) getOrElse(symbolsParamsMap, "extra", 0),
                    (String) getOrElse(symbolsParamsMap, "type", "standard"),
                    (String) getOrElse(symbolsParamsMap, "impact", "none")
                )
            );
        }

        return new Config(
                configDTO.rows(),
                configDTO.columns(),
                symbols

        );
    }

    private static <T> Object getOrElse(JsonObject jobj, String key, T defaultVal) {
        if(jobj.has(key)) {
            return switch (defaultVal.getClass().getName()) {
                case "java.lang.String" -> jobj.get(key).getAsString();
                case "java.lang.Integer" -> jobj.get(key).getAsInt();
                case "java.lang.Double" -> jobj.get(key).getAsDouble();
                default -> jobj.get(key);
            };

        } else return defaultVal;
    }


}
