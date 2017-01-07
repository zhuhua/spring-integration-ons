package org.springframework.integration.ons.outbound;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.OnExceptionContext;
import com.aliyun.openservices.ons.api.SendCallback;
import com.aliyun.openservices.ons.api.SendResult;
import org.springframework.integration.ons.core.OnsClientFactory;

/**
 * Created by hupa on 2017/1/4.
 */
public class OnsMessageHandler extends AbstractOnsMessageHandler implements SendCallback {

    private volatile boolean async = false;

    public OnsMessageHandler(OnsClientFactory onsClientFactory) {
        super(onsClientFactory);
    }

    @Override
    protected void publish(Object onsMessage) throws Exception {

        Message message = (Message) onsMessage;
        message.setTopic(this.getTopic());

        if (!async) {
            this.getProducer().send(message);
        } else {
            this.getProducer().sendAsync(message, this);
        }

    }

    @Override
    public void onSuccess(SendResult sendResult) {

    }

    @Override
    public void onException(OnExceptionContext context) {

    }

    public void setAsync(boolean async) {
        this.async = async;
    }


}
