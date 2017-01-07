package org.springframework.integration.ons.support;

import org.springframework.messaging.Message;
import org.springframework.messaging.converter.MessageConverter;

/**
 * Created by hupa on 2017/1/5.
 */
public interface OnsMessageConverter extends MessageConverter {

    Message<?> toMessage(com.aliyun.openservices.ons.api.Message onsMessage);
}
