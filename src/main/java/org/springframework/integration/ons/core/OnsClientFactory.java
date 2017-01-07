package org.springframework.integration.ons.core;

import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.Producer;

/**
 * Created by hupa on 2017/1/5.
 */
public interface OnsClientFactory {

    Producer getProducer();

    Consumer getConsumer();

}
