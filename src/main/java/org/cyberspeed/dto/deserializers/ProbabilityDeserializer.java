package org.cyberspeed.dto.deserializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import org.cyberspeed.dto.ProbabilitySymbol;

import java.lang.reflect.Type;
import java.util.List;

public class ProbabilityDeserializer implements JsonDeserializer<List<ProbabilitySymbol>> {
    @Override
    public List<ProbabilitySymbol> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return List.of();
    }
}
