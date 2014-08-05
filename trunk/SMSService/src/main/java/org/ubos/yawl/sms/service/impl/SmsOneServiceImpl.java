package org.ubos.yawl.sms.service.impl;

import com.ubos.yawl.sms.utils.Settings;
import org.apache.log4j.Logger;
import org.omnitech.sms.smsone.SmsOneSmsApi;
import org.ubos.yawl.sms.service.SMSService;


public class SmsOneServiceImpl implements SMSService {

    protected Logger log = Logger.getLogger(this.getClass());

    public void sendSMS(String number, String sender, String message) {
        if (number.contains(",")) {
            String[] nums = number.split(",");
            for (String num : nums) {
                sendSms0(num, sender, message);
            }
        } else {
            sendSms0(number, sender, message);
        }
    }

    private void sendSms0(String number, String sender, String message) {
        SmsOneSmsApi api = new SmsOneSmsApi();
        api.setSendSmsUrl("//bulksms.omnitech.co.ug:8866/cgi-bin/sendsms");
        api.setCreditsUrl("//bulksms.omnitech.co.ug:8866/cgi-bin/requestbalance");
        api.setUserName(Settings.readSetting("smsone.username"));
        api.setPassword(Settings.readSetting("smsone.password"));

        sender = (sender == null || sender.isEmpty()) ? "M4W" : sender;
        try {
            log.debug(
                    "Sending SMS: [" + number + "][" + message + "] " + api.sendSms(sender, number, message)
            );
        } catch (Exception ex) {
            log.error("Error while sending SMS:to  " + sender, ex);
        }
    }

    public static void main(String[] args) {
        new SmsOneServiceImpl().sendSMS("256712075579", null, "Single");
        new SmsOneServiceImpl().sendSMS("256712075579,256712075579", null, "Comma Separated");
    }
}
