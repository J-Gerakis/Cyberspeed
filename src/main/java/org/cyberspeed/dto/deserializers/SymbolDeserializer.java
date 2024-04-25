package org.cyberspeed.dto.deserializers;

import com.google.gson.*;
import org.cyberspeed.dto.Symbol;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.cyberspeed.dto.deserializers.Utils.getOrElse;

public class SymbolDeserializer implements JsonDeserializer<List<Symbol>> {
    @Override
    public List<Symbol> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject symbolsDtoMap = json.getAsJsonObject();
        List<Symbol> symbols = new ArrayList<>();

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
        return symbols;

    }


}
