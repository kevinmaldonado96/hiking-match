package org.example.hikingroutesservice.rabbitmq.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfiguration {

    @Value("${rabbitmq.new.topic.name}")
    private String routeNewTopicExchangeName;

    @Value("${rabbitmq.new.queue.name}")
    private String routeNewQueueName;

    @Value("${rabbitmq.new.routing.key}")
    private String routeNewRoutingKey;

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(routeNewTopicExchangeName);
    }

    @Bean
    public Queue routeQueue(){
        return new Queue(routeNewQueueName);
    }

    @Bean
    public Binding biding(Queue routeQueue, TopicExchange topicExchange){
        return BindingBuilder.bind(routeQueue).to(topicExchange).with(routeNewRoutingKey);
    }





}
