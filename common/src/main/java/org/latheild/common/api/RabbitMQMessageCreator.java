package org.latheild.common.api;

import org.latheild.common.constant.MessageType;
import org.latheild.common.domain.Message;

public class RabbitMQMessageCreator {
    public static Message newInstance(MessageType messageType, Object messageBody) {
        Message message = new Message();
        message.setMessageType(messageType);
        message.setMessageBody(messageBody);
        return message;
    }
}
