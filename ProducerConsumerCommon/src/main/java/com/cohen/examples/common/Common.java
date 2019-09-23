package com.cohen.examples.common;

import java.util.Objects;

public class Common {

    public static final String TOPIC_NAME = "sample_topic";
    public static final String DEFAULT_SERVER = "kafka:9092";
    public static final String SERVER_PROPERTY = "KAFKA_SERVER";


    public static String getServer() {
        String env = System.getenv(SERVER_PROPERTY);
        return Objects.nonNull(env) ? env : DEFAULT_SERVER;
    }
}
