package org.springframework.integration.ons.config;

import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.w3c.dom.Element;


/**
 * Created by hupa on 2017/1/5.
 */
public class OnsParserUtils {

    private OnsParserUtils() {
        throw new AssertionError();
    }

    public static void parseCommon(Element element, BeanDefinitionBuilder builder, ParserContext parserContext) {

        ConstructorArgumentValues.ValueHolder holder;

        String clientFactory = element.getAttribute("client-factory");
        builder.addConstructorArgReference(clientFactory);

        String topic = element.getAttribute("topic");
        builder.addPropertyValue("topic", topic);

        IntegrationNamespaceUtils.setReferenceIfAttributeDefined(builder, element, "converter");
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "send-timeout");
    }

}
