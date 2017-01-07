package org.springframework.integration.ons.config;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.AbstractOutboundChannelAdapterParser;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.integration.ons.outbound.OnsMessageHandler;
import org.w3c.dom.Element;

/**
 * Created by hupa on 2017/1/4.
 */
public class OnsOutboundChannelAdapterParser extends AbstractOutboundChannelAdapterParser {

    @Override
    protected boolean shouldGenerateId() {
        return false;
    }

    @Override
    protected boolean shouldGenerateIdAsFallback() {
        return true;
    }

    @Override
    protected AbstractBeanDefinition parseConsumer(Element element, ParserContext parserContext) {

        final BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(OnsMessageHandler.class);

        OnsParserUtils.parseCommon(element, builder, parserContext);

        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "topic");
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "async");
        return builder.getBeanDefinition();
    }
}
