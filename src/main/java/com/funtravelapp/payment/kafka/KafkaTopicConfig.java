package com.funtravelapp.payment.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic updateStatusOrderTopic(){
        return TopicBuilder.name("UpdateStatusOrder").build();
    }

    @Bean
    public NewTopic createNotifTopic(){
        return TopicBuilder.name("CreateNotif").build();
    }

    @Bean
    public NewTopic updateNotifStatus(){
        return TopicBuilder.name("UpdateNotifStatus").build();
    }

    @Bean
    public NewTopic updateStatusPayment(){
        return TopicBuilder.name("UpdateStatusPayment").build();
    }
}
