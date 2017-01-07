package org.springframework.integration.ons.config;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.AbstractChannelAdapterParser;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.integration.ons.inbound.OnsMessageDrivenChannelAdapter;
import org.w3c.dom.Element;

/**
 * Created by hupa on 2017/1/4.
 */
public class OnsMessageDrivenChannelAdapterParser extends AbstractChannelAdapterParser {

    @Override
    protected AbstractBeanDefinition doParse(Element element, ParserContext parserContext, String channelName) {

        BeanDefinitionBuilder builder = BeanDefinitionBuilder
                .genericBeanDefinition(OnsMessageDrivenChannelAdapter.class);

        OnsParserUtils.parseCommon(element, builder, parserContext);

        builder.addPropertyReference("outputChannel", channelName);
        IntegrationNamespaceUtils.setReferenceIfAttributeDefined(builder, element, "error-channel");

        return builder.getBeanDefinition();
    }
}
