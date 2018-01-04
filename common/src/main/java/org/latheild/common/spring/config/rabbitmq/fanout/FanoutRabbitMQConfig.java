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
    public Queue CommentReceiverQueue() {
        return new Queue(COMMENT_QUEUE);
    }

    @Bean
    public FanoutExchange UserFanoutExchange() {
        return new FanoutExchange(USER_FAN_OUT_EXCHANGE);
    }

    @Bean
    public FanoutExchange ProjectFanoutExchange() {
        return new FanoutExchange(PROJECT_FAN_OUT_EXCHANGE);
    }

    @Bean
    public FanoutExchange ProgressFanoutExchange() {
        return new FanoutExchange(PROGRESS_FAN_OUT_EXCHANGE);
    }

    @Bean FanoutExchange TaskFanoutExchange() {
        return new FanoutExchange(TASK_FAN_OUT_EXCHANGE);
    }

    @Bean
    public Binding bindingUserExchangeUserInfo(Queue UserInfoReceiverQueue, FanoutExchange UserFanoutExchange) {
        return BindingBuilder.bind(UserInfoReceiverQueue).to(UserFanoutExchange);
    }

    @Bean
    public Binding bindingUserExchangeProject(Queue ProjectReceiverQueue, FanoutExchange UserFanoutExchange) {
        return BindingBuilder.bind(ProjectReceiverQueue).to(UserFanoutExchange);
    }

    @Bean
    public Binding bindingUserExchangeComment(Queue CommentReceiverQueue, FanoutExchange UserFanoutExchange) {
        return BindingBuilder.bind(CommentReceiverQueue).to(UserFanoutExchange);
    }

    @Bean
    public Binding bindingProjectExchangeProgress(Queue ProgressReceiverQueue, FanoutExchange ProjectFanoutExchange) {
        return BindingBuilder.bind(ProgressReceiverQueue).to(ProjectFanoutExchange);
    }

    @Bean
    public Binding bindingProgressExchangeTask(Queue TaskReceiverQueue, FanoutExchange ProgressFanoutExchange) {
        return BindingBuilder.bind(TaskReceiverQueue).to(ProgressFanoutExchange);
    }

    @Bean
    public Binding bindingTaskExchangeSubtask(Queue SubtaskReceiverQueue, FanoutExchange TaskFanoutExchange) {
        return BindingBuilder.bind(SubtaskReceiverQueue).to(TaskFanoutExchange);
    }

    @Bean
    public Binding bindingTaskExchangeComment(Queue CommentReceiverQueue, FanoutExchange TaskFanoutExchange) {
        return BindingBuilder.bind(CommentReceiverQueue).to(TaskFanoutExchange);
    }
}
