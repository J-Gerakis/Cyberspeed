package org.cyberspeed.dto;

import com.google.gson.JsonObject;

public record ConfigFile(
        Integer columns,
        Integer rows,
        JsonObject symbols
) {}
