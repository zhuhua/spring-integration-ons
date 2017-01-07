package org.springframework.integration.ons;

import org.apache.log4j.Logger;
import org.springframework.messaging.Message;

/**
 * Created by hupa on 2017/1/6.
 */
public class OnsEndpoint {

    private Logger log = Logger.getLogger(OnsEndpoint.class);

    public void receive(Message<String> message) {
        log.info("Subscribe Msg " + message);
    }

}
