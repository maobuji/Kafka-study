package com.fan.kafka.study.stream.wordcount;

import com.fan.kafka.study.util.PropertiesUtils;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * Created by Administrator on 2018/8/13.
 */
public class ShowWordCount {

    public static void main(String[] args) throws Exception {

        Properties props = PropertiesUtils.getProperties("rec.properties");

        // key的反序列化方式
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        // value的反序列化方式
        props.put("value.deserializer", "org.apache.kafka.common.serialization.LongDeserializer");

        Consumer<String, Long> consumer = new KafkaConsumer<String, Long>(props);
        // 监听的队列，支持对多队列的监听
        consumer.subscribe(Collections.singletonList("testStreamWordCountTopic"));


        while (true) {
            ConsumerRecords<String, Long> records = consumer.poll(Duration.ofSeconds(100));
            for (ConsumerRecord<String, Long> record : records) {
                System.out.println("" + record.offset() + " " + record.key() + " " + record.value() + "\n");
            }
        }
    }

}
