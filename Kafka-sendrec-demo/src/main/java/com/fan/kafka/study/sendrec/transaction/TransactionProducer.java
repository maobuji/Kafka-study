package com.fan.kafka.study.sendrec.transaction;

import com.fan.kafka.study.util.PropertiesUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * Created by Administrator on 2018/8/13.
 */
public class TransactionProducer implements Runnable {


    private String transationID;

    private String producerKey;


    public TransactionProducer(String producerKey, String transationID) {
        this.producerKey = producerKey;
        this.transationID = transationID;
    }

    public void sendMessage() throws Exception {
        Properties props = PropertiesUtils.getProperties("send.properties");

        // key的序列化方式
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // value的序列化方式
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        props.put("transactional.id", transationID);

        Producer<String, String> producer = new KafkaProducer<String, String>(props);



        producer.initTransactions();
        producer.beginTransaction();
        try {
            for (int i = 0; i < 10; i++) {

                Thread.sleep(2000);

                String topic = "testTransctionTopic";
                String key = "" + System.nanoTime();
                String value = "消息" + i;

                // 形成kafka消息
                ProducerRecord producerRecord = new ProducerRecord<String, String>(topic, key, value);
                // 直接发送消息
                producer.send(producerRecord);
                // 等待到发送成功才返回
                // producer.send(producerRecord).get();
                // 等待三秒后超时
                // producer.send(producerRecord).get(3, TimeUnit.SECONDS);
                System.out.println("producer" + producerKey + "发送消息到队列" + topic + ":key=" + key + " value=" + value);
            }
            producer.commitTransaction();
        } catch (Exception ex) {
            // 回滚事物
            producer.abortTransaction();
            System.out.println("producer" + producerKey + "回滚事物");
        } finally {
            producer.close();
        }
    }


    @Override
    public void run() {
        try {
            sendMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
