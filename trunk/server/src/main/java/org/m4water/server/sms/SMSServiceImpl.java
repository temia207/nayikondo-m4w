package org.m4water.server.sms;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;


import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author kay
 */
@Component("smsService")
public class SMSServiceImpl  {
	private  String M4W = "M4W";

    private org.slf4j.Logger log = LoggerFactory.getLogger(SMSServiceImpl.class);
    
        public void sendSMS(String number, String message) {
	    //FIXME
//	    if(0<1) {
//		log.debug("Sending message: <" + message + "> to " + number);
//		return;
//	    }
                try {
                        String bodyContent = executeSMSGet(number, message);
                        log.debug("Server Replied: " + bodyContent);

                        if (bodyContent == null) {
                                log.debug("SMS Server experienced and error with request");
                        }

                        boolean sendSuccesful = isSendSuccesful(bodyContent);

                        if (sendSuccesful)
                                log.debug("Sending message: <" + message + "> to " + number);
                        else {
                                log.debug("Sending Message Failed: <" + getMessageString(number, message) + "> WIth Status Code <" + bodyContent + ">");
                        }
                } catch (IOException ex) {
                        log.error("Error While Sending message: " + getMessageString(number, message),ex);
                } catch (URISyntaxException ex) {
                        log.error("Failed sending message: Check the specified URL " + ex.getMessage());
                        return;
                }

        }

        private String executeSMSGet(String number, String message) throws URISyntaxException, IOException {
                URI uri = buildUrl(number, message);

                HttpClient client = new DefaultHttpClient();
                HttpResponse httpResponse = client.execute(new HttpGet(uri));
                log.debug("Connecting to SMS Server...");

                StatusLine statusLine = httpResponse.getStatusLine();
                if (statusLine.getStatusCode() != 200) {
                        log.info("Failed seneding Reminder message to" + getMessageString(number, message) + " HTTP: Error Code: " + statusLine.getStatusCode() + " " + statusLine.getReasonPhrase());
                        return null;
                }

                HttpEntity entity = httpResponse.getEntity();

                if (entity == null) {
                        log.info("Server Replied with Invalid response for message: " + getMessageString(number, message));
                } else {
                        InputStream content = entity.getContent();
                        return IOUtils.toString(content);
                }
                return null;
        }

        public URI buildUrl(String number, String message) throws URISyntaxException {
                List<NameValuePair> qparams = new ArrayList<NameValuePair>();
                qparams.add(new BasicNameValuePair("username", "kayondor@gmail.com"));
                qparams.add(new BasicNameValuePair("password", "m4w!!2012"));
                qparams.add(new BasicNameValuePair("recipients", number));
                qparams.add(new BasicNameValuePair("from", M4W));
                qparams.add(new BasicNameValuePair("message", message+""));
                qparams.add(new BasicNameValuePair("token", "c4ca4238a0b923820dcc509a6f75849b"));
                qparams.add(new BasicNameValuePair("type", "normal"));
                //http://208.111.47.244/api.php 
                URI uri = URIUtils.createURI("http", "208.111.47.244", 80, "/api.php",
                        URLEncodedUtils.format(qparams, "UTF-8"), null);
               log.debug(uri.toString());
                return uri;
        }

        private String getMessageString(String number, String message) {
                return " <" + number + "> message <" + message + ">.";
        }

        private boolean isSendSuccesful(String content) throws IOException {
                return hasCode("Error", content);
        }

        private boolean hasCode(String code, String response) {
                String trim = response.trim();
                return trim.startsWith(code + ":");
        }
	
	public static void main(String[] args) {
		SMSServiceImpl imp = new SMSServiceImpl();
		imp.M4W = "M4w";
		imp.sendSMS("256775144070", "http://goo.gl/BRdcr");
	}
}
