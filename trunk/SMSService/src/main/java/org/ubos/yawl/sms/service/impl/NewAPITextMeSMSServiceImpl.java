package org.ubos.yawl.sms.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import org.ubos.yawl.sms.service.SMSService;

/**
 *
 * @author kay
 */
public class NewAPITextMeSMSServiceImpl extends BaseHTTPGetSMS implements SMSService {

    public List<NameValuePair> getNameValuePairs(String number, String message) {
        List<NameValuePair> qparams = new ArrayList<NameValuePair>();
        qparams.add(new BasicNameValuePair("username", "kayondor@gmail.com"));
        qparams.add(new BasicNameValuePair("password", "m4w!!2012"));
        qparams.add(new BasicNameValuePair("recipients", number));
        qparams.add(new BasicNameValuePair("from", "M4W"));
        qparams.add(new BasicNameValuePair("message", message + ""));
        qparams.add(new BasicNameValuePair("token", "c4ca4238a0b923820dcc509a6f75849b"));
        qparams.add(new BasicNameValuePair("type", "normal"));
        return qparams;
    }

   

    public boolean isSendSuccesful(String content) throws IOException {
        return hasCode("Error", content);
    }

    private boolean hasCode(String code, String response) {
        String trim = response.trim();
        return trim.startsWith(code + ":");
    }

    @Override
    public String getPath() {
        return "/api.php";
    }

    @Override
    public int getPort() {
        return 80;
    }

    @Override
    public String getHost() {
        return "208.111.47.244";
    }

    @Override
    public String getProtocol() {
        return "http";
    }

    public static void main(String[] args) {
        NewAPITextMeSMSServiceImpl impl = new NewAPITextMeSMSServiceImpl();
        impl.sendSMS("256712075579", "KAJJDLADk");
    }

    @Override
    public String getErrorMsg(String bodyContent) {
     return "Unknown Error";
    }
}
