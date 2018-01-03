package org.latheild.common.spring.config.rabbitmq.fanout;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;

import static org.latheild.common.constant.RabbitMQExchange.*;
import static org.latheild.common.constant.RabbitMQQueue.*;

public class FanoutRabbitMQConfig {
    @Bean
    public Queue UserInfoReceiverQueue() {
        return new Queue(USER_INFO_QUEUE);
    }

    @Bean
    public Queue ProjectReceiverQueue() {
        return new Queue(PROJECT_QUEUE);
    }

    @Bean
    public Queue ProgressReceiverQueue() {
        return new Queue(PROGRESS_QUEUE);
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

    @Bean
    public FanoutExchange projectFanoutExchange() {
        return new FanoutExchange(PROJECT_FAN_OUT_EXCHANGE);
    }

    @Bean
    public FanoutExchange progressFanoutExchange() {
        return new FanoutExchange(PROGRESS_FAN_OUT_EXCHANGE);
    }

    @Bean FanoutExchange taskFanoutExchange() {
        return new FanoutExchange(TASK_FAN_OUT_EXCHANGE);
    }

    @Bean
    public Binding bindingUserExchangeUserInfo(Queue UserInfoReceiverQueue, FanoutExchange userFanoutExchange) {
        return BindingBuilder.bind(UserInfoReceiverQueue).to(userFanoutExchange);
    }

    @Bean
    public Binding bindingUserExchangeProject(Queue ProjectReceiverQueue, FanoutExchange userFanoutExchange) {
        return BindingBuilder.bind(ProjectReceiverQueue).to(userFanoutExchange);
    }

    @Bean
    public Binding bindingProjectExchangeProgress(Queue ProgressReceiverQueue, FanoutExchange projectFanoutExchange) {
        return BindingBuilder.bind(ProgressReceiverQueue).to(projectFanoutExchange);
    }

    @Bean
    public Binding bindingProgressExchangeTask(Queue TaskReceiverQueue, FanoutExchange progressFanoutExchange) {
        return BindingBuilder.bind(TaskReceiverQueue).to(progressFanoutExchange);
    }

    @Bean
    public Binding bindingTaskExchangeSubtask(Queue SubtaskReceiverQueue, FanoutExchange taskFanoutExchange) {
        return BindingBuilder.bind(SubtaskReceiverQueue).to(taskFanoutExchange);
    }
}
