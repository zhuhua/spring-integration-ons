package org.springframework.integration.ons.support;

/**
 * Created by hupa on 2017/1/5.
 */
public final class OnsHeaders {

    private static final String PREFIX = "ons__";

    public static final String TOPIC = PREFIX + "topic";
    public static final String TAG = PREFIX + "tag";
    public static final String KEY = PREFIX + "key";
    public static final String MSG_ID = PREFIX + "msg_id";
    public static final String RECONSUME_TIMES = PREFIX + "reconsume_times";
    public static final String START_DELIVER_TIME = PREFIX + "start_deliver_time";

    private OnsHeaders() {
        throw new AssertionError();
    }

}
