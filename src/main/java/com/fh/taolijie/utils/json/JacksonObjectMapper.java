package com.fh.taolijie.utils.json;
 
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
public class JacksonObjectMapper extends ObjectMapper {
 
 private static final Logger log = LoggerFactory.getLogger(JacksonObjectMapper.class);
 
 public JacksonObjectMapper() {
   configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
  }
}
