package consumer;

import common.Common;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ConsumerHelper {

    private final static String BOOTSTRAP_SERVERS = "localhost:9092";

    public static void main(String[] args) {
        runConsumer();
    }

    static void runConsumer()  {
        final Consumer<Long, String> consumer = createConsumer();

        Runtime.getRuntime().addShutdownHook(new Thread(consumer::close));

        while (true) {
            final ConsumerRecords<Long, String> consumerRecords = consumer.poll(Duration.of(1000, ChronoUnit.MILLIS));

            if (consumerRecords.count() == 0) {
                continue;
            }

            consumerRecords.forEach(record -> {
                System.out.printf("[Consumer] Message Received:\n  %s\n", record.value());
            });

            consumer.commitAsync();
        }
    }

    private static Consumer<Long, String> createConsumer() {
        final Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "KafkaExampleConsumer");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // Create the consumer using props.
        final Consumer<Long, String> consumer = new KafkaConsumer<>(props);

        // Subscribe to the topic.
        consumer.subscribe(Collections.singletonList(Common.TOPIC_NAME));

        return consumer;
    }
}
