package com.fan.kafka.study.sendrec.transaction;

import com.fan.kafka.study.util.PropertiesUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.UUID;

/**
 * Created by Administrator on 2018/8/7.
 */
public class Send {

    public static void main(String[] args) throws Exception {


        String uuid= UUID.randomUUID().toString();
        TransactionProducer transactionProducer1=new TransactionProducer("1",uuid);

        TransactionProducer transactionProducer2=new TransactionProducer("2",uuid);


        Thread t1=new Thread(transactionProducer1);
        t1.setDaemon(false);
        t1.start();

        Thread.sleep(6000);

        Thread t2=new Thread(transactionProducer2);
        t2.setDaemon(false);
        t2.start();
    }
}
