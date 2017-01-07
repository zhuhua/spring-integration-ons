package org.springframework.integration.ons.core;

import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.Properties;

/**
 * Created by hupa on 2017/1/5.
 */
public class DefaultOnsClientFactory implements OnsClientFactory, InitializingBean {

    private String accessKey;
    private String secretKey;
    private String onsAddr;

    private String producerId;
    private String consumerId;

    private Properties properties = new Properties();

    public DefaultOnsClientFactory() {
        super();
    }

    public DefaultOnsClientFactory(String accessKey, String secretKey, String onsAddr,
                                   String producerId, String consumerId) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.onsAddr = onsAddr;
        this.producerId = producerId;
        this.consumerId = consumerId;
        this.init();
    }

    public void init() {

        Assert.notNull(this.accessKey, "'accessKey' can not be null.");
        Assert.notNull(this.secretKey, "'secretKey' can not be null.");
        Assert.notNull(this.onsAddr, "'onsAddr' can not be null.");
        Assert.notNull(this.producerId, "'producerId' can not be null.");
        Assert.notNull(this.consumerId, "'consumerId' can not be null.");

        properties.put(PropertyKeyConst.AccessKey, this.accessKey);
        properties.put(PropertyKeyConst.SecretKey, this.secretKey);
        properties.put(PropertyKeyConst.ONSAddr, this.onsAddr);
        properties.put(PropertyKeyConst.ProducerId, this.producerId);
        properties.put(PropertyKeyConst.ConsumerId, this.consumerId);
    }

    @Override
    public Producer getProducer() {
        return ONSFactory.createProducer(this.properties);
    }

    @Override
    public Consumer getConsumer() {
        return ONSFactory.createConsumer(this.properties);
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setOnsAddr(String onsAddr) {
        this.onsAddr = onsAddr;
    }

    public void setProducerId(String producerId) {
        this.producerId = producerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.init();
    }
}
