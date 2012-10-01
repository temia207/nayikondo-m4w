/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.m4water.server.sms;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author kay
 */
public abstract class BaseHTTPGetSMS {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    public URI buildUrl(String number, String message) throws URISyntaxException {
        List<NameValuePair> qparams = getNameValuePairs(number, message);
        //http://208.111.47.244/api.php
        URI uri = URIUtils.createURI(getProtocol(), getHost(), getPort(), getPath(), URLEncodedUtils.format(qparams, "UTF-8"), null);
        	printUrl(uri);
        return uri;
    }

	protected void printUrl(URI uri) {
		log.debug(uri.toString());
	}

    public String executeSMSGet(String number, String message) throws URISyntaxException, IOException {
        URI uri = buildUrl(number, message);
        HttpClient client = new DefaultHttpClient();
        HttpResponse httpResponse = client.execute(new HttpPost(uri));
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

    public void sendSMS(String number, String message) {
        try {
            String bodyContent = executeSMSGet(number, message);
            log.debug("Server Replied: " + bodyContent);
            if (bodyContent == null) {
                log.error("SMS Server experienced and error with request");
            }
            boolean sendSuccesful = isSendSuccesful(bodyContent);
            if (sendSuccesful) {
                log.debug("Sending message: <" + message + "> to " + number);
            } else {
                log.error("Sending Message Failed: <" + getMessageString(number, message) + "> WIth Status Code <" + bodyContent + ">: "+getErrorMsg(bodyContent) );
            }
        } catch (IOException ex) {
            log.error("Error While Sending message: " + getMessageString(number, message), ex);
        } catch (URISyntaxException ex) {
            log.debug("Failed sending message: " + ex.getMessage());
            return;
        }
    }

    public abstract String getPath();

    public abstract int getPort();

    public abstract String getHost();

    public String getProtocol() {
        return "http";
    }

     public String getMessageString(String number, String message) {
        return " <" + number + "> message <" + message + ">.";
    }

    public abstract List<NameValuePair> getNameValuePairs(String number, String message);

    public abstract boolean isSendSuccesful(String content) throws IOException;

    public abstract String getErrorMsg(String bodyContent);
}
