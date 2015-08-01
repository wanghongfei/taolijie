package com.fh.taolijie.utils.json;

import java.io.IOException;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.annotate.JsonSerialize;

//import com.nestorurquiza.serialization.JacksonObjectMapper;

public final class JsonUtils {
    private static JacksonObjectMapper mapper = new JacksonObjectMapper();

    private JsonUtils() {

    }

    public static String toJson(Object value) throws JsonGenerationException, JsonMappingException, IOException {
        // NULL字段不序列化
        mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        return mapper.writeValueAsString(value);
    }
}
