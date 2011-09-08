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


import org.springframework.stereotype.Component;

/**
 *
 * @author kay
 */
@Component("smsService")
public class SMSServiceImpl  {

      
        public void sendSMS(String number, String message) {
                try {
                        String bodyContent = executeSMSGet(number, message);
                        System.out.println("Server Replied: " + bodyContent);

                        if (bodyContent == null) {
                                System.out.println("SMS Server experienced and error with request");
                        }

                        boolean sendSuccesful = isSendSuccesful(bodyContent);

                        if (!sendSuccesful && shouldResend(bodyContent))
                                sendSMS(number, message);


                        if (sendSuccesful)
                                System.out.println("Sending message: <" + message + "> to " + number);
                        else {
                                System.out.println("Sending Message Failed: <" + getMessageString(number, message) + "> WIth Status Code <" + bodyContent + ">");
                        }
                } catch (IOException ex) {
                        System.out.println("Error While Sending message: " + getMessageString(number, message));
                        ex.printStackTrace();
                } catch (URISyntaxException ex) {
                        System.out.println("Failed sending message: " + ex.getMessage());
                        return;
                }

        }

        private String executeSMSGet(String number, String message) throws URISyntaxException, IOException {
                URI uri = buildUrl(number, message);

                HttpClient client = new DefaultHttpClient();
                HttpResponse httpResponse = client.execute(new HttpGet(uri));
                System.out.println("Executing URL for Server...");

                StatusLine statusLine = httpResponse.getStatusLine();
                if (statusLine.getStatusCode() != 200) {
                        System.out.println("Failed seneding Reminder message to" + getMessageString(number, message) + " HTTP: Error Code: " + statusLine.getStatusCode() + " " + statusLine.getReasonPhrase());
                        return null;
                }

                HttpEntity entity = httpResponse.getEntity();

                if (entity == null) {
                        System.out.println("Server Replied with Invalid response for message: " + getMessageString(number, message));
                } else {
                        InputStream content = entity.getContent();
                        return IOUtils.toString(content);
                }
                return null;
        }

        public URI buildUrl(String number, String message) throws URISyntaxException {
                List<NameValuePair> qparams = new ArrayList<NameValuePair>();
                qparams.add(new BasicNameValuePair("email", "kayondor@gmail.com"));
                qparams.add(new BasicNameValuePair("password", "0872a1"));
                qparams.add(new BasicNameValuePair("destination", number));
                qparams.add(new BasicNameValuePair("source", "M4W"));
                qparams.add(new BasicNameValuePair("message", message+""));
                URI uri = URIUtils.createURI("http", "TextMe.UG", 80, "/bulk_api/get.php3",
                        URLEncodedUtils.format(qparams, "UTF-8"), null);
                HttpGet httpget = new HttpGet(uri);
                System.out.println(httpget.getURI());
                return uri;
        }

        private String getMessageString(String number, String message) {
                return " <" + number + "> message <" + message + ">.";
        }

        private boolean isSendSuccesful(String content) throws IOException {
                return hasCode("8000", content);
        }

        private boolean shouldResend(String content) {
                return hasCode("6000", content);
        }

        private boolean hasCode(String code, String response) {
                String trim = response.trim();
                return trim.startsWith(code + "|");
        }
}
