package org.springframework.integration.ons.outbound;

import com.aliyun.openservices.ons.api.Producer;
import org.springframework.context.Lifecycle;
import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.integration.ons.core.OnsClientFactory;
import org.springframework.integration.ons.support.DefaultMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.MessageConverter;

import java.util.Objects;

/**
 * Created by hupa on 2017/1/5.
 */
public abstract class AbstractOnsMessageHandler extends AbstractMessageHandler implements Lifecycle {

    private OnsClientFactory onsClientFactory;

    private Producer producer;

    private volatile MessageConverter converter;

    private volatile String topic;

    protected AbstractOnsMessageHandler(OnsClientFactory onsClientFactory) {
        this.onsClientFactory = onsClientFactory;
    }

    @Override
    protected void onInit() throws Exception {
        this.producer = onsClientFactory.getProducer();
        if (converter == null) {
            this.converter = new DefaultMessageConverter();
        }
        super.onInit();
    }

    @Override
    public void start() {
        this.producer.start();
    }

    @Override
    public void stop() {
        if (this.producer != null) {
            this.producer.shutdown();
        }
    }

    @Override
    public boolean isRunning() {
        return this.producer.isStarted();
    }

    @Override
    protected void handleMessageInternal(Message<?> message) throws Exception {
        Object onsMessage = converter.fromMessage(message, Objects.class);
        this.publish(onsMessage);
    }

    protected abstract void publish(Object onsMessage) throws Exception;

    protected Producer getProducer() {
        return producer;
    }

    protected MessageConverter getConverter() {
        return converter;
    }

    public void setConverter(MessageConverter converter) {
        this.converter = converter;
    }

    protected String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

}
