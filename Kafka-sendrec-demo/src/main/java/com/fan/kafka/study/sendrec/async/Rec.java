package com.fan.kafka.study.sendrec.async;

import com.fan.kafka.study.util.PropertiesUtils;
import kafka.common.OffsetMetadata;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
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
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        // 改为手动提交
        props.put("enable.auto.commit", "false");

        // 一次拉下来的记录条数,自动提交是在poll的时候进行提交的
        props.put("max.poll.records", 20);


        Consumer<String, String> consumer = new KafkaConsumer<String, String>(props);

        // 监听的队列，支持对多队列的监听
        consumer.subscribe(Collections.singletonList("testAsyncTopic"));

        int i = 0;
        while (true) {
            i++;
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(5L));
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("从队列" + record.topic() + "接收消息：offset=" + record.offset()
                        + " key=" + record.key()
                        + " value=" + record.value().toString()
                        + " partition=" + record.partition()
                        + " timestamp=" + record.timestamp());
                sleep(1000);

                if (i % 10 == 0) {
                    commitKafkaOffset(consumer, record);
                }

            }
            commitKafka(consumer);
        }

    }


    public static void commitKafkaOffset(Consumer<String, String> consumer, ConsumerRecord<String, String> record) {
        long offset = record.offset();
        int partition = record.partition();
        String topic = record.topic();
        TopicPartition topicPartition = new TopicPartition(topic, partition);
        Map<TopicPartition, OffsetAndMetadata> map = Collections.singletonMap(topicPartition, new OffsetAndMetadata(offset + 1L));

        // 同步提交
        consumer.commitSync(map);

        // 异步提交必须要callback才行
        //consumer.commitAsync(map,null);

    }


    /**
     * 手动更新kafka-offset只针对MessageType.File
     */
    public static void commitKafka(Consumer<String, String> consumer) {
        if (null != consumer) {
            // 异步提交，失败不报错
            consumer.commitAsync();

            // 同步提交，直到失败或者成功
            // consumer.commitSync();
            System.out.println("提交-----------------------");
        }
    }

    public static void sleep(long sleepTime) {

        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
