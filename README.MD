 kafka-docker with producer service and consumer
============

This docker create 4 containers: 
1. zookeeper
2. kafka
3. producer + http service (spring)
4. consumer (Java app)


Run docker-compose up

After all containers are up to check all is working run:


curl -X POST http://localhost:9999/kafka_sample/message -H "Content-Type: text/plain" -d "My Message"
