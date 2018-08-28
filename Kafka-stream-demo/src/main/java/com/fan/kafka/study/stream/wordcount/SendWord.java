package com.fan.kafka.study.stream.wordcount;

import com.fan.kafka.study.util.PropertiesUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.Scanner;

/**
 * Created by Administrator on 2018/8/13.
 */
public class SendWord {

    public static void main(String[] args) throws Exception {


        Properties props = PropertiesUtils.getProperties("send.properties");

        // key的序列化方式
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // value的序列化方式
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");


        Producer<String, String> producer = new KafkaProducer<String, String>(props);

        Scanner scanner = new Scanner(System.in);
        int number = 0;

        String topic = "testStreamSendWordTopic";
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line == null || line.equalsIgnoreCase("bye")) {
                break;
            }
            number++;
            producer.send(new ProducerRecord<String, String>(topic, "" + number, line));
        }
        producer.close();
    }
}
