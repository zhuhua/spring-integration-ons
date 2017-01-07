package org.springframework.integration.ons;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.ons.support.OnsHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * Created by hupa on 2017/1/5.
 */
@ContextConfiguration(value = "classpath:OnsAdapterTests-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class OnsAdapterTests {

    @Autowired
    private OnsGateway onsGateway;

    private Logger log = Logger.getLogger(OnsAdapterTests.class);

    @Test
    public void testOutboundAdapter() throws InterruptedException {
//        String msg = "This.is.test";
//        onsGateway.publish(msg);
//        log.info("Publish Msg " + msg);
        Thread.sleep(10000L);
    }

    @Test
    public void testDelay() throws InterruptedException {
        Date deliverTime = new Date(System.currentTimeMillis() + 3000);
        MessageBuilder<String> builder = MessageBuilder.withPayload("This.a.delay.message");
        builder.setHeader(OnsHeaders.START_DELIVER_TIME, deliverTime.getTime());
        Message<String> msg = builder.build();
        onsGateway.publish(builder.build());
        log.info("Publish Msg " + msg + " Deliver Time is " + deliverTime);
        Thread.sleep(10000L);
    }

}
