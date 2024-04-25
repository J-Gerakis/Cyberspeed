package org.cyberspeed.dto;

import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
import org.cyberspeed.dto.deserializers.SymbolDeserializer;

import java.util.List;

public record ConfigFile(
        Integer columns,
        Integer rows,
        @JsonAdapter(SymbolDeserializer.class)
        List<Symbol> symbols,
        JsonObject probabilities
) {}
