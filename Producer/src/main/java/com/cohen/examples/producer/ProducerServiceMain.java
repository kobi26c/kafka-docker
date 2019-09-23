package com.cohen.examples.producer;

import com.cohen.examples.common.KafkaUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Properties;

@SpringBootApplication
public class ProducerServiceMain {

        public static void main(String[] args) {
            SpringApplication.run(ProducerServiceMain.class, args);
        }


    @Bean
    public Producer<Long, String> kafkaProducer() {
            Properties props = new Properties();
            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaUtils.getServer());
            props.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaExampleProducer");
            props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
            props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

            Producer<Long, String> producer = new KafkaProducer<>(props);
            Runtime.getRuntime().addShutdownHook(new Thread(producer::close));
            return producer;
    }

}
