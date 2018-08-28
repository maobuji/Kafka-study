package com.fan.kafka.study.stream.wordcount;

import com.fan.kafka.study.util.PropertiesUtils;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.KeyValueStore;

import java.util.Arrays;
import java.util.Properties;

/**
 * Created by Administrator on 2018/8/13.
 */
public class StreamWord {


    public static void main(String[] args) throws Exception {

        String dataSourceTopic="testStreamSendWordTopic";
        String dataTargetTopic="testStreamWordCountTopic";

        Properties props = PropertiesUtils.getProperties("stream.properties");


        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-application2");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> textLines = builder.stream(dataSourceTopic);
        KTable<String, Long> wordCounts = textLines
                .flatMapValues(textLine -> {
                    return Arrays.asList(textLine.toLowerCase().split("\\W+"));
                })
                .groupBy((key, word) -> {
                    try {
                        System.out.println("key is =>" + key);
                    } catch (Exception e) {

                    }
                    System.out.println("value is =>" + word);
                    return word;
                })
                .count(Materialized.<String, Long, KeyValueStore<Bytes, byte[]>>as("counts-store"));
        wordCounts.toStream().to(dataTargetTopic, Produced.with(Serdes.String(), Serdes.Long()));

        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();
    }
}
