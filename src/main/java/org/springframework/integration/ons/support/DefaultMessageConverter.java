package org.springframework.integration.ons.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.integration.support.AbstractIntegrationMessageBuilder;
import org.springframework.integration.support.DefaultMessageBuilderFactory;
import org.springframework.integration.support.MessageBuilderFactory;
import org.springframework.integration.support.utils.IntegrationUtils;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.MessageConversionException;

import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by hupa on 2017/1/5.
 */
public class DefaultMessageConverter implements OnsMessageConverter, BeanFactoryAware {

    private final String charset = Charset.defaultCharset().name();

    private volatile boolean payloadAsBytes = false;

    private volatile BeanFactory beanFactory;

    private volatile MessageBuilderFactory messageBuilderFactory = new DefaultMessageBuilderFactory();

    private volatile boolean messageBuilderFactorySet;


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public Message<?> toMessage(com.aliyun.openservices.ons.api.Message onsMessage) {

        try {
            AbstractIntegrationMessageBuilder<Object> messageBuilder = getMessageBuilderFactory()
                    .withPayload(onsBytesToPayload(onsMessage))
                    .setHeader(OnsHeaders.TOPIC, onsMessage.getTopic())
                    .setHeader(OnsHeaders.KEY, onsMessage.getKey())
                    .setHeader(OnsHeaders.TAG, onsMessage.getTag())
                    .setHeader(OnsHeaders.MSG_ID, onsMessage.getMsgID())
                    .setHeader(OnsHeaders.RECONSUME_TIMES, onsMessage.getReconsumeTimes())
                    .setHeader(OnsHeaders.START_DELIVER_TIME, onsMessage.getStartDeliverTime());

            Properties userProps = onsMessage.getUserProperties();
            Set<Object> propKeys = userProps.keySet();
            for (Object key : propKeys) {
                if (!(key instanceof String)) {
                    continue;
                }

                try {
                    String userPropKey = (String) key;
                    messageBuilder.setHeader(userPropKey, userProps.getProperty(userPropKey));
                } catch (Exception e) {
                    continue;
                }
            }

            return messageBuilder.build();
        } catch (Exception e) {
            throw new MessageConversionException("failed to convert object to Message", e);
        }
    }

    @Override
    public Object fromMessage(Message<?> message, Class<?> targetClass) {

        Map<String, Object> headers = new HashMap<>(message.getHeaders());

        com.aliyun.openservices.ons.api.Message onsMessage = new com.aliyun.openservices.ons.api.Message(
                headers.get(OnsHeaders.TAG) == null ? "" : String.valueOf(headers.remove(OnsHeaders.TAG)),
                headers.get(OnsHeaders.KEY) == null ? "" : String.valueOf(headers.remove(OnsHeaders.KEY)),
                messageToOnsBytes(message)
        );

        Object msgId = headers.remove(OnsHeaders.MSG_ID);
        if (msgId != null) {
            onsMessage.setMsgID((String) msgId);
        }


        try {
            onsMessage.setReconsumeTimes((Integer) headers.remove(OnsHeaders.RECONSUME_TIMES));
        } catch (Exception e) {

        }

        try {
            onsMessage.setStartDeliverTime((Long) headers.remove(OnsHeaders.START_DELIVER_TIME));
        } catch (Exception e) {

        }

        Set<String> keys = headers.keySet();
        for (String key : keys) {
            Object userProp = headers.get(key);
            if (userProp == null) {
                continue;
            }

            onsMessage.putUserProperties(key, String.valueOf(userProp));
        }

        return onsMessage;
    }

    @Override
    public Message<?> toMessage(Object onsMessage, MessageHeaders headers) {
        if (!(onsMessage instanceof com.aliyun.openservices.ons.api.Message)) {
            throw new IllegalArgumentException("This converter can only convert an 'OnsMessage'; received: "
                    + onsMessage.getClass().getName());
        }
        return toMessage((com.aliyun.openservices.ons.api.Message) onsMessage);
    }

    protected MessageBuilderFactory getMessageBuilderFactory() {
        if (!this.messageBuilderFactorySet) {
            if (this.beanFactory != null) {
                this.messageBuilderFactory = IntegrationUtils.getMessageBuilderFactory(this.beanFactory);
            }
            this.messageBuilderFactorySet = true;
        }
        return this.messageBuilderFactory;
    }

    /**
     * Subclasses can override this method to convert the byte[] to a payload.
     * The default implementation creates a String (default) or byte[].
     *
     * @param onsMessage The inbound message.
     * @return The payload for the Spring integration message
     * @throws Exception Any.
     */
    protected Object onsBytesToPayload(com.aliyun.openservices.ons.api.Message onsMessage) throws Exception {
        if (this.payloadAsBytes) {
            return onsMessage.getBody();
        } else {
            return new String(onsMessage.getBody(), this.charset);
        }
    }

    /**
     * Subclasses can override this method to convert the payload to a byte[].
     * The default implementation accepts a byte[] or String payload.
     *
     * @param message The outbound Message.
     * @return The byte[] which will become the payload of the ONS Message.
     */
    protected byte[] messageToOnsBytes(Message<?> message) {
        Object payload = message.getPayload();
        if (!(payload instanceof byte[] || payload instanceof String)) {
            throw new IllegalArgumentException(
                    "This default converter can only handle 'byte[]' or 'String' payloads; consider adding a "
                            + "transformer to your flow definition, or subclass this converter for "
                            + payload.getClass().getName() + " payloads");
        }
        byte[] payloadBytes;
        if (payload instanceof String) {
            try {
                payloadBytes = ((String) payload).getBytes(this.charset);
            } catch (Exception e) {
                throw new MessageConversionException("failed to convert Message to object", e);
            }
        } else {
            payloadBytes = (byte[]) payload;
        }
        return payloadBytes;
    }
}
