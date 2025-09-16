package com.georgiiHadzhiev.roomservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic roomEventsTopic() {
        return TopicBuilder.name("room.events")
                .partitions(1)
                .replicas(1)
                .build();
    }

}
