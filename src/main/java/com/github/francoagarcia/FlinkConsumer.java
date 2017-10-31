package com.github.francoagarcia;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer082;
import org.apache.flink.streaming.util.serialization.DeserializationSchema;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;

import java.util.Properties;

public class FlinkConsumer {
	
	public static void main(String[] args) throws Exception {
		// create execution environment
		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
		
		// parse user parameters
		ParameterTool parameterTool = ParameterTool.fromArgs(args);
		
		String topic = parameterTool.getRequired("topic");
		DeserializationSchema<String> valueDeserializer = new SimpleStringSchema();
		Properties consumerProps = parameterTool.getProperties();
		
		FlinkKafkaConsumer082<String> consumerConfig = new FlinkKafkaConsumer082<>(topic, valueDeserializer, consumerProps);
		
		DataStream<String> messageStream = env.addSource( consumerConfig );
		
		// the rebalance() call is causing a repartitioning of the data so that all machines
		// see the messages (for example in cases when "num kafka partitions" < "num flink operators"
		messageStream.rebalance()
				.map((String value) -> "Stream: " + value)
				.print();
		
		env.execute();
	}
}
