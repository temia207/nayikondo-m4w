/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.m4water.server.sms;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.openxdata.yawl.util.Settings;

/**
         
        1701 = Success
        1702 = Invalid URl Error
        1703 = Invalid value in username or password field
        1704 = Invalid value in \"type\" field
        1705 = Invalid Message
        1706 = Invalid Destination
        1707 = Invalid Source (Sender)
        1708 = Invalid value for \"DLR\" field
        1709 = User validation failed
        1710 = Internal Error
        1025 = Insufficient Credit
         
 * @author kay
 */
public class RouteSMSServiceImpl extends BaseHTTPGetSMS {

    private String username;
    private String password;

    public RouteSMSServiceImpl() {
        username = Settings.readSetting("username");
        password = Settings.readSetting("password");
        log.info("Read From Prop File: " + username + "-" + (password == null? "null":"***"));
    }

    @Override
    public String getErrorMsg( String message) {

        if(hasCode("1701",message)) return "Success"; else
        if(hasCode("1702",message)) return "Invalid URl Error"; else
        if(hasCode("1703",message)) return "Invalid value in username or password field"; else
        if(hasCode("1704",message)) return "Invalid value in \"type\" field"; else
        if(hasCode("1705",message)) return "Invalid Message"; else
        if(hasCode("1706",message)) return "Invalid Destination"; else
        if(hasCode("1707",message)) return "Invalid Source (Sender)"; else
        if(hasCode("1708",message)) return "Invalid value for \"DLR\" field"; else
        if(hasCode("1709",message)) return "User validation failed"; else
        if(hasCode("1710",message)) return "Internal Error"; else
        if(hasCode("1025",message)) return "Insufficient Credit"; else
                                    return "Unknown Error";
    }

    @Override
    public List<NameValuePair> getNameValuePairs(String number, String message) {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("username", username));
        nameValuePairs.add(new BasicNameValuePair("password", password));
        nameValuePairs.add(new BasicNameValuePair("type", "0"));
        nameValuePairs.add(new BasicNameValuePair("dlr", "1"));
        nameValuePairs.add(new BasicNameValuePair("destination", number));
        nameValuePairs.add(new BasicNameValuePair("source", "M4W"));
        nameValuePairs.add(new BasicNameValuePair("message", message));

        return nameValuePairs;
    }

    @Override
    public boolean isSendSuccesful(String content) throws IOException {
        return hasCode("1701", content);
    }

    @Override
    public String getPath() {
        return "/bulksms/bulksms";
    }

    @Override
    public int getPort() {
        return 8080;
    }

    @Override
    public String getHost() {
        return "smpp4.routesms.com";
    }

    private boolean hasCode(String code, String response) {
        String trim = response.trim();
        return trim.startsWith(code + "|");
    }

	@Override
	protected void printUrl(URI uri) {
		String url = uri.toString();
		url =url.replace("="+password, "=***");
		log.debug(url);
	}
    


    public static void main(String[] args) {
        RouteSMSServiceImpl impl = new RouteSMSServiceImpl();
        //impl.sendSMS("256704269020", "Halloooooo");
	impl.sendSMS("256712075579", "Halloooooo");
    }
}
