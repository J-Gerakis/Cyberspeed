package org.cyberspeed.dto;

import com.google.gson.JsonObject;

public class Utils {


    public static <T> Object getOrElse(JsonObject jobj, String key, T defaultVal) {
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
