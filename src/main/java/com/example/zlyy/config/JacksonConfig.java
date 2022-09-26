package com.example.zlyy.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

@Configuration
public class JacksonConfig {

    private static final Logger logger = LoggerFactory.getLogger(JacksonConfig.class);

    @Bean
    @Primary
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                String fieldName = jsonGenerator.getOutputContext().getCurrentName();
                try {
                    //反射获取字段类型
                    Field field = jsonGenerator.getCurrentValue().getClass().getDeclaredField(fieldName);
                    
                    logger.info("field type: {}" + field.getType());
                    
                    if (CharSequence.class.isAssignableFrom(field.getType())) {
                        //字符串型空值""
                        jsonGenerator.writeString("");
                        return;
                    } else if (Integer.class.isAssignableFrom(field.getType())) {
                        jsonGenerator.writeNumber(0);
                        return;
                    } else if (Collection.class.isAssignableFrom(field.getType())) {
                        //列表型空值返回[]
                        jsonGenerator.writeStartArray();
                        jsonGenerator.writeEndArray();
                        return;
                    } else if (Map.class.isAssignableFrom(field.getType())) {
                        //map型空值 或者 bean对象 返回{}
                        jsonGenerator.writeStartObject();
                        jsonGenerator.writeEndObject();
                        return;
                    }
                } catch (NoSuchFieldException ignored) {
                }

                jsonGenerator.writeString("");
            }
        });
        return objectMapper;
    }
}

//@Configuration
//public class JacksonConfig {
//
//    @Bean
//    @Primary
//    @ConditionalOnMissingBean(ObjectMapper.class)
//    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
//
//        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
//
//        objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer() {
//
//            @Override
//            public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
//                jsonGenerator.writeString("");
//                jsonGenerator.writeNumber(0);
//            }
//        });
//
//        return objectMapper;
//
//    }
//}
