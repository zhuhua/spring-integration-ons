package org.springframework.integration.ons.inbound;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.MessageListener;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.integration.ons.core.OnsClientFactory;
import org.springframework.messaging.Message;

/**
 * Created by hupa on 2017/1/5.
 */
public class OnsMessageDrivenChannelAdapter extends AbstractOnsMessageDrivenChannelAdapter
        implements MessageListener, ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;

    public OnsMessageDrivenChannelAdapter(OnsClientFactory onsClientFactory) {
        super(onsClientFactory);
    }

    @Override
    protected void doStart() {
        super.doStart();
        this.getConsumer().subscribe(this.getTopic(), this.getSubExpression(), this);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public Action consume(com.aliyun.openservices.ons.api.Message onsMessage, ConsumeContext context) {

        Message<?> message = this.getConverter().toMessage(onsMessage);

        try {
            this.sendMessage(message);
        } catch (RuntimeException e) {
            logger.error("Unhandled exception for " + message.toString(), e);
            throw e;
        }

        return Action.CommitMessage;
    }
}
