package org.ubos.yawl.sms.service;

/**
 *
 * @author kay
 */
public interface SMSService {

        public void sendSMS(String number, String sender, String message);
}
