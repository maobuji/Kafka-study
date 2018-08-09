package com.fan.kafka.study.sendrec.stringvalue;

import com.fan.kafka.study.util.PropertiesUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/8/7.
 */
public class Send {

    public static void main(String[] args) throws Exception {

        Properties props = PropertiesUtils.getProperties("send.properties");

        // key的序列化方式
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // value的序列化方式
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer<String, String>(props);


        for (int i = 0; i < 9; i++) {

            // 形成kafka消息
            ProducerRecord producerRecord = new ProducerRecord<String, String>("testStringTopic", "key:" + System.nanoTime(), "消息" + i);

            // 直接发送消息
            producer.send(producerRecord);

            // 等待到发送成功才返回
            // producer.send(producerRecord).get();

            // 等待三秒后超时
            // producer.send(producerRecord).get(3, TimeUnit.SECONDS);

        }
        producer.close();

    }
}
