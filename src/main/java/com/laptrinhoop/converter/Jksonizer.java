package com.laptrinhoop.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.laptrinhoop.constants.Constant;
import lombok.experimental.UtilityClass;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class Jksonizer {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    static {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);

        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(Constant.DateTimeFormat.YYYY_MM_DD)));
        module.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(Constant.DateTimeFormat.YYYY_MM_DD)));
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(Constant.DateTimeFormat.YYYY_MM_DD_HH_MM_SS)));
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(Constant.DateTimeFormat.YYYY_MM_DD_HH_MM_SS)));
        objectMapper.registerModule(module);
    }

    public static String toJson(Object payload) {
        try {
            return objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            return e.getMessage();
        }
    }

    public static <T> T fromJson(String value, Class<T> classOfT) {
        try {
            return objectMapper.readValue(value, classOfT);
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> T fromJson(String value, TypeReference<T> type) {
        try {
            return objectMapper.readValue(value, type);
        } catch (Exception e) {
           return null;
        }
    }

    public static JacksonConverterFactory getConverterFactory() {
        return JacksonConverterFactory.create(objectMapper);
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
