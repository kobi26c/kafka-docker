package com.cohen.examples.producer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.MediaType;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@RestController
@RequestMapping("kafka_sample")
public class ProducerRestService {

    private Producer<Long, String> kafkaProducer;
    private Gson gson;

    @Autowired
    public ProducerRestService(Producer<Long, String> kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @PostMapping(path = "message", consumes = MediaType.TEXT_PLAIN)
    public void addMessage(@RequestBody String message) throws Exception {
        OutputMessage outputMessage = new OutputMessage(message,  LocalDateTime.now(ZoneId.of("Asia/Jerusalem")));
        ProducerHelper.runProducer(kafkaProducer, gson.toJson(outputMessage));
    }

    private static class OutputMessage {
        private String message;
        private String timestamp;

        OutputMessage(String message, LocalDateTime timestamp) {
            this.message = message;
            this.timestamp = timestamp.toString();
        }

        public String getMessage() {
            return message;
        }

        public String getTimestamp() {
            return timestamp;
        }
    }
}
