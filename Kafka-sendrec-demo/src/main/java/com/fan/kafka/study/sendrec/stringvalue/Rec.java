package com.fan.kafka.study.sendrec.stringvalue;

import com.fan.kafka.study.util.PropertiesUtils;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;


import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * Created by Administrator on 2018/8/7.
 */
public class Rec {
    public static void main(String[] args) throws Exception {
        Properties props = PropertiesUtils.getProperties("rec.properties");

        // key的反序列化方式
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        // value的反序列化方式
        props.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");

        Consumer<String, String> consumer = new KafkaConsumer<String, String>(props);

        // 监听的队列，支持对多队列的监听
        consumer.subscribe(Collections.singletonList("testStringTopic"));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(5L));
        }

    }
}
