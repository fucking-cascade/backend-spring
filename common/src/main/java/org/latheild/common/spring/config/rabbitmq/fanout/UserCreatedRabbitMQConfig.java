package org.latheild.common.spring.config.rabbitmq.fanout;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;

import static org.latheild.common.constant.RabbitMQExchange.USER_CREATED_FAN_OUT_EXCHANGE;
import static org.latheild.common.constant.RabbitMQQueue.TASK_QUEUE;
import static org.latheild.common.constant.RabbitMQQueue.USER_INFO_QUEUE;

public class UserCreatedRabbitMQConfig {
    @Bean
    public Queue UserInfoReceiverQueue() {
        return new Queue(USER_INFO_QUEUE);
    }

    @Bean
    public Queue TaskReceiverQueue() {
        return new Queue(TASK_QUEUE);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(USER_CREATED_FAN_OUT_EXCHANGE);
    }

    @Bean
    public Binding bindingExchangeUserInfo(Queue UserInfoReceiverQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(UserInfoReceiverQueue).to(fanoutExchange);
    }

    @Bean
    public Binding bindingExchangeTask(Queue TaskReceiverQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(TaskReceiverQueue).to(fanoutExchange);
    }
}
