package com.fh.taolijie.utils.json;

import java.io.IOException;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParser;
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
        //mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

        String json = mapper.writeValueAsString(value);

        return json.replaceAll("'", "\\\\'");
    }

    public static void main(String[] args) {
        String json = "{\"asd\":\"as'as\"}";



        System.out.println(json);
        System.out.println(json.replaceAll("'", "\\\\'"));
    }

/*    public static String format(String jsonStr) {
        int level = 0;
        StringBuffer jsonForMatStr = new StringBuffer();
        for(int i=0;i<jsonStr.length();i++){
            char c = jsonStr.charAt(i);
            if(level>0&&'\n'==jsonForMatStr.charAt(jsonForMatStr.length()-1)){
                jsonForMatStr.append(getLevelStr(level));
            }
            switch (c) {
                case '{':
                case '[':
                    jsonForMatStr.append(c+"\n");
                    level++;
                    break;
                case ',':
                    jsonForMatStr.append(c+"\n");
                    break;
                case '}':
                case ']':
                    jsonForMatStr.append("\n");
                    level--;
                    jsonForMatStr.append(getLevelStr(level));
                    jsonForMatStr.append(c);
                    break;
                default:
                    jsonForMatStr.append(c);
                    break;
            }
        }

        return jsonForMatStr.toString();

    }

    private static String getLevelStr(int level){
        StringBuffer levelStr = new StringBuffer();
        for(int levelI = 0;levelI<level ; levelI++){
            levelStr.append("\t");
        }
        return levelStr.toString();
    }*/
}
