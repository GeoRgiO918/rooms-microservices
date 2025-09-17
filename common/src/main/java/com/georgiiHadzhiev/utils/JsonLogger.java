package com.georgiiHadzhiev.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonLogger {

    private final Logger logger;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public JsonLogger(Class<?> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Class cannot be null");
        }
        this.logger = LoggerFactory.getLogger(clazz);
        objectMapper.registerModule(new JavaTimeModule());
    }

    public JsonLogger(Logger logger){
        this.logger = logger;
        objectMapper.registerModule(new JavaTimeModule());
    }


    public void info(String message, Object obj) {
        logger.info("{} {}", message, toJson(obj));
    }

    public void debug(String message, Object obj) {
        logger.debug("{} {}", message, toJson(obj));
    }

    public void warn(String message, Object obj) {
        logger.warn("{} {}", message, toJson(obj));
    }

    public void error(String message, Object obj, Throwable t) {
        logger.error("{} {}", message, toJson(obj), t);
    }

    private String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return "Could not serialize object: " + obj;
        }
    }
}
