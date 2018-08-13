package com.fan.kafka.study.sendrec.transaction;

import com.fan.kafka.study.util.PropertiesUtils;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * Created by Administrator on 2018/8/7.
 *
 */
public class Rec {
    public static void main(String[] args) throws Exception {
        Properties props = PropertiesUtils.getProperties("rec.properties");


        props.put("group.id","0");


        // key的反序列化方式
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        // value的反序列化方式
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        Consumer<String, String> consumer = new KafkaConsumer<String, String>(props);

        // 监听的队列，支持对多队列的监听
        consumer.subscribe(Collections.singletonList("testTransctionTopic"));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(5L));
            for (ConsumerRecord<String, String> record : records) {

                System.out.println("从队列" + record.topic() + "接收消息：offset=" + record.offset()
                        + " key=" + record.key()
                        + " value=" + record.value().toString()
                        + " partition=" + record.partition()
                        + " timestamp=" + record.timestamp());

            }
        }

    }
}
