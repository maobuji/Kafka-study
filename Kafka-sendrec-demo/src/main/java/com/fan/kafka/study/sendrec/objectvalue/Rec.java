package com.fan.kafka.study.sendrec.objectvalue;

import com.fan.kafka.study.info.Person;
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
 */
public class Rec {
    public static void main(String[] args) throws Exception {
        Properties props = PropertiesUtils.getProperties("rec.properties");

        // key的反序列化方式
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        // value的反序列化方式
        props.put("value.deserializer", "com.fan.kafka.study.util.ObjectDeserializer");

        Consumer<String, Object> consumer = new KafkaConsumer<String, Object>(props);

        // 监听的队列，支持对多队列的监听
        consumer.subscribe(Collections.singletonList("testObjectTopic"));

        while (true) {
            ConsumerRecords<String, Object> records = consumer.poll(Duration.ofSeconds(5L));
            for (ConsumerRecord<String, Object> record : records) {

                System.out.println("从队列" + record.topic() + "接收消息：offset=" + record.offset()
                        + " key=" + record.key()
                        + " value=" + ((Person)record.value()).toString()
                        + " partition=" + record.partition()
                        + " timestamp=" + record.timestamp());
            }
        }

    }
}
