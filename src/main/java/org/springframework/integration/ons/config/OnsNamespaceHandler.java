package org.springframework.integration.ons.config;

import org.springframework.integration.config.xml.AbstractIntegrationNamespaceHandler;

/**
 * Created by hupa on 2017/1/4.
 */
public class OnsNamespaceHandler extends AbstractIntegrationNamespaceHandler {

    @Override
    public void init() {
        this.registerBeanDefinitionParser("message-driven-channel-adapter",  new OnsMessageDrivenChannelAdapterParser());
        this.registerBeanDefinitionParser("outbound-channel-adapter", new OnsOutboundChannelAdapterParser());
    }

}
