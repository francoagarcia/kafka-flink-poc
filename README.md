# Prueba de concepto de Apache Kafka con Apache Flink

## Instalar Kafka

```sbtshell
wget http://mirror.softaculous.com/apache//kafka/0.8.2.1/kafka_2.10-0.8.2.1.tgz
tar xf kafka_2.10-0.8.2.1.tgz
cd kafka_2.10-0.8.2.1
```

## Levantamos Kafka y un Producer

1. Levantamos Zookeeper

```sbtshell
bin/zookeeper-server-start.sh config/zookeeper.properties
```

2. Levantamos un Broker

```sbtshell
bin/kafka-server-start.sh config/server.properties
```

3. Creamos el topic "ejemplo"

```sbtshell
bin/kafka-topics.sh --create --topic ejemplo --zookeeper localhost:2181 --partitions 1 --replication-factor 1
```

4. Levantamos un producer

```sbtshell
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic ejemplo
```

5. Levantamos un consumer 

```sbtshell
bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic ejemplo --from-beginning
```

## Levantamos nuestra Consumer Application

1. Levantamos nuestra aplicación Consumer con Flink pasandole la configuracion por parametro

```sbtshell
--topic ejemplo --bootstrap.servers localhost:9092 --zookeeper.connect localhost:2181 --group.id myGroup
```