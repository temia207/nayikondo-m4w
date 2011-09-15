package org.ubos.yawl.sms.service.impl;

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

import org.apache.log4j.Logger;
import org.ubos.yawl.sms.service.SMSService;

/**
 *
 * @author kay
 */
public class SMSServiceImpl implements SMSService {

        private static Logger log = Logger.getLogger(SMSServiceImpl.class);

        public void sendSMS(String number, String message) {
                try {
                        String bodyContent = executeSMSGet(number, message);
                        log.debug("Server Replied: " + bodyContent);

                        if (bodyContent == null) {
                                log.error("SMS Server experienced and error with request");
                        }

                        boolean sendSuccesful = isSendSuccesful(bodyContent);

                        if (!sendSuccesful && shouldResend(bodyContent))
                                sendSMS(number, message);


                        if (sendSuccesful)
                                log.debug("Sending message: <" + message + "> to " + number);
                        else {
                                log.error("Sending Message Failed: <" + getMessageString(number, message) + "> WIth Status Code <" + bodyContent + ">");
                        }
                } catch (IOException ex) {
                        log.error("Error While Sending message: " + getMessageString(number, message), ex);
                } catch (URISyntaxException ex) {
                        log.debug("Failed sending message: " + ex.getMessage());
                        return;
                }

        }

        private String executeSMSGet(String number, String message) throws URISyntaxException, IOException {
                URI uri = buildUrl(number, message);

                HttpClient client = new DefaultHttpClient();
                HttpResponse httpResponse = client.execute(new HttpGet(uri));
                log.debug("Executing URL for Server...");

                StatusLine statusLine = httpResponse.getStatusLine();
                if (statusLine.getStatusCode() != 200) {
                        log.error("Failed seneding Reminder message to" + getMessageString(number, message) + " HTTP: Error Code: " + statusLine.getStatusCode() + " " + statusLine.getReasonPhrase());
                        return null;
                }

                HttpEntity entity = httpResponse.getEntity();

                if (entity == null) {
                        log.error("Server Replied with Invalid response for message: " + getMessageString(number, message));
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
