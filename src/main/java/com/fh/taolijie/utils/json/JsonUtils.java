package com.fh.taolijie.utils.json;

import java.io.IOException;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

//import com.nestorurquiza.serialization.JacksonObjectMapper;

public final class JsonUtils {
    private static JacksonObjectMapper mapper = new JacksonObjectMapper();

    private JsonUtils() {

    }

    public static String toJson(Object value) throws JsonGenerationException, JsonMappingException, IOException {
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        return mapper.writeValueAsString(value);
    }
}
