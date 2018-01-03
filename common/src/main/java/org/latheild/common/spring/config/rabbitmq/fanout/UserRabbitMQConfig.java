package org.latheild.common.spring.config.rabbitmq.fanout;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;

import static org.latheild.common.constant.RabbitMQExchange.TASK_FAN_OUT_EXCHANGE;
import static org.latheild.common.constant.RabbitMQExchange.USER_FAN_OUT_EXCHANGE;
import static org.latheild.common.constant.RabbitMQQueue.SUBTASK_QUEUE;
import static org.latheild.common.constant.RabbitMQQueue.TASK_QUEUE;
import static org.latheild.common.constant.RabbitMQQueue.USER_INFO_QUEUE;

public class UserRabbitMQConfig {
    @Bean
    public Queue UserInfoReceiverQueue() {
        return new Queue(USER_INFO_QUEUE);
    }

    @Bean
    public Queue TaskReceiverQueue() {
        return new Queue(TASK_QUEUE);
    }

    @Bean
    public Queue SubtaskReceiverQueue() {
        return new Queue(SUBTASK_QUEUE);
    }

    @Bean
    public FanoutExchange userFanoutExchange() {
        return new FanoutExchange(USER_FAN_OUT_EXCHANGE);
    }

    @Bean FanoutExchange taskFanoutExchange() {
        return new FanoutExchange(TASK_FAN_OUT_EXCHANGE);
    }

    @Bean
    public Binding bindingUserExchangeUserInfo(Queue UserInfoReceiverQueue, FanoutExchange userFanoutExchange) {
        return BindingBuilder.bind(UserInfoReceiverQueue).to(userFanoutExchange);
    }

    @Bean
    public Binding bindingUserExchangeTask(Queue TaskReceiverQueue, FanoutExchange userFanoutExchange) {
        return BindingBuilder.bind(TaskReceiverQueue).to(userFanoutExchange);
    }

    @Bean
    public Binding bindingTaskExchangeSubtask(Queue SubtaskReceiverQueue, FanoutExchange taskFanoutExchange) {
        return BindingBuilder.bind(SubtaskReceiverQueue).to(taskFanoutExchange);
    }
}
