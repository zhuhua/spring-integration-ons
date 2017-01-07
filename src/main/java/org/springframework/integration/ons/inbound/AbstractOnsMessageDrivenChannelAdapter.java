package org.springframework.integration.ons.inbound;

import com.aliyun.openservices.ons.api.Consumer;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.ons.core.OnsClientFactory;
import org.springframework.integration.ons.support.DefaultMessageConverter;
import org.springframework.integration.ons.support.OnsMessageConverter;
import org.springframework.integration.support.management.IntegrationManagedResource;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * Created by hupa on 2017/1/5.
 */
@ManagedResource
@IntegrationManagedResource
public class AbstractOnsMessageDrivenChannelAdapter extends MessageProducerSupport {

    private OnsClientFactory onsClientFactory;

    private volatile Consumer consumer;

    private volatile OnsMessageConverter converter;

    private volatile String topic;

    private volatile String subExpression;

    protected AbstractOnsMessageDrivenChannelAdapter(OnsClientFactory onsClientFactory) {
        this.onsClientFactory = onsClientFactory;
    }

    protected Consumer getConsumer() {
        return consumer;
    }

    protected OnsMessageConverter getConverter() {
        return converter;
    }

    public void setConverter(OnsMessageConverter converter) {
        this.converter = converter;
    }

    @Override
    protected void onInit() {
        this.consumer = onsClientFactory.getConsumer();
        if (converter == null) {
            this.converter = new DefaultMessageConverter();
        }
        super.onInit();
    }

    @Override
    protected void doStart() {
        this.consumer.start();
    }

    @Override
    protected void doStop() {
        if (this.consumer != null) {
            this.consumer.shutdown();
        }
    }

    protected String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    protected String getSubExpression() {
        return subExpression == null ? "" : subExpression;
    }

    public void setSubExpression(String subExpression) {
        this.subExpression = subExpression;
    }
}
