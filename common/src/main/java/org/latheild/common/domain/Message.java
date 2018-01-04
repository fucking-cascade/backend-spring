package org.latheild.common.domain;

import org.latheild.common.constant.MessageType;

import java.io.Serializable;

public class Message implements Serializable {
    private MessageType messageType;

    private Object messageBody;

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public Object getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(Object messageBody) {
        this.messageBody = messageBody;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageType=" + messageType +
                ", messageBody='" + messageBody + '\'' +
                '}';
    }
}
