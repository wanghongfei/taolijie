package com.fh.taolijie.utils.json;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

//import com.nestorurquiza.serialization.JacksonObjectMapper;

public final class JsonUtils {

    private JsonUtils() {
    }

    public static String toJson(Object value) throws JsonGenerationException, JsonMappingException, IOException {
        JacksonObjectMapper mapper = new JacksonObjectMapper();
        return mapper.writeValueAsString(value);
    }
}
