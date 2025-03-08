#!/bin/bash

sleep 10

echo "init kafka start"
# Kafka 토픽 생성
/bin/kafka-topics --bootstrap-server broker-1:19092,broker-2:19092,broker-3:19092 \
  --create --topic crawl --partitions 3 --replication-factor 3

/bin/kafka-topics --bootstrap-server broker-1:19092,broker-2:19092,broker-3:19092 \
  --create --topic refinedNews --partitions 3 --replication-factor 3

sleep 1

echo "init kafka end"

