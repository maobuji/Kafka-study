package com.fan.kafka.study.sendrec.objectvalue;

import com.fan.kafka.study.info.Person;
import com.fan.kafka.study.util.PropertiesUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * Created by Administrator on 2018/8/7.
 */
public class Send {

    public static void main(String[] args) throws Exception {


        Properties props = PropertiesUtils.getProperties("send.properties");

        // key的序列化方式
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // value的序列化方式
        props.put("value.serializer", "com.fan.kafka.study.util.ObjectSerializer");
        Producer<String, Object> producer = new KafkaProducer<String, Object>(props);

        for (int i = 0; i < 9; i++) {

            String topic = "testObjectTopic";
            String key = "" + System.nanoTime();
            Person value = new Person("人员"+i,i);

            // 形成kafka消息
            ProducerRecord producerRecord = new ProducerRecord<String, Object>(topic, key, value);

            // 直接发送消息
            producer.send(producerRecord);

            // 等待到发送成功才返回
            // producer.send(producerRecord).get();

            // 等待三秒后超时
            // producer.send(producerRecord).get(3, TimeUnit.SECONDS);

            System.out.println("发送消息到队列" + topic + ":key=" + key + " value=" + value);

        }
        producer.close();

    }
}
