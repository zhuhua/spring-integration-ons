package org.springframework.integration.ons;


import org.springframework.messaging.Message;

/**
 * Created by hupa on 2017/1/6.
 */
public interface OnsGateway {

    void publish(String message);

    void publish(Message<String> message);

}

