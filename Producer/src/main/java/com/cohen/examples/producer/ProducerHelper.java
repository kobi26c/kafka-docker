package com.cohen.examples.producer;

import com.cohen.examples.common.KafkaUtils;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;


public class ProducerHelper {


    static void runProducer(Producer<Long, String> producer, String message) throws Exception {

        try {
            final ProducerRecord<Long, String> record = new ProducerRecord<>(KafkaUtils.TOPIC_NAME, message);

            RecordMetadata metadata = producer.send(record).get();
            System.out.printf("sent record(key=%s value=%s) " + "meta(partition=%d, offset=%d)\n",
                    record.key(), record.value(), metadata.partition(), metadata.offset());

        } finally {
            producer.flush();
        }
    }

//    private static Producer<Long, String> createProducer() {
//        Properties props = new Properties();
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
//        props.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaExampleProducer");
//        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//        return new KafkaProducer<>(props);
//    }
}
