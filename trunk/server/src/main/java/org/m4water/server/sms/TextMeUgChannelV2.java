package org.m4water.server.sms;

import com.google.gson.Gson;
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
import org.muk.fcit.results.sms.*;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author kay
 */
public class TextMeUgChannelV2 implements Channel {

    private org.slf4j.Logger log = LoggerFactory.getLogger(TextMeUgChannelV2.class);
    private String hostNameOrVariable;

    private List<SMSMessage> bufferedMessages = new ArrayList<SMSMessage>();
    private String path;
    private int port;
    private String protocol;
    private Gson gs = new Gson();

    private int highestId;

    public TextMeUgChannelV2() {
        port = 80;
        path = "/sms/query.php";
        protocol = "http";
        hostNameOrVariable = "m4water.org";
    }

    public TextMeUgChannelV2(int highestId) {
        this();
        this.highestId = highestId;
    }


    public SMSMessage read() throws ChannelException {
        open();
        while (bufferedMessages.isEmpty()) {
            try {
                log.trace("*******Wating for new messages***********");
                waitForNewMessages();
                log.trace("***********Got new messages**************");
            } catch (Exception ex) {
                throw new ChannelException(ex);
            }
        }
        return bufferedMessages.remove(0);
    }


    private void waitForNewMessages() throws IOException, TimeoutException, URISyntaxException {
        List<TextMeMessage> newMsgArrivals = null;
        for (; ; ) {
            log.trace("");
            log.trace("@----------------------------------");
            try {
                String executeSMSGet = executeSMSGet();
                //log.trace(executeSMSGet.substring(0, 50), null, "TextMe.UG.Channel=" + this.hostNameOrVariable);
                newMsgArrivals = parseJsonString(executeSMSGet);
                log.trace("@And Got [" + newMsgArrivals.size() + "] new Message Arrivals");
                log.trace("@Highest id for last messages is  [" + highestId + "]");
                if (executeSMSGet == null || newMsgArrivals.isEmpty()) {
                    log.trace("@Now Sleeping for 3 Seconds...");
                    Thread.sleep(5000);
                } else {
                    log.trace("@----------------------------------");
                    break;
                }

            } catch (InterruptedException ex) {
                Logger.getLogger(TextMeUgChannelV2.class.getName()).log(Level.SEVERE, null, ex);
            }
            log.trace("@----------------------------------");
        }
        bufferTheMessages(newMsgArrivals);
    }

    private void bufferTheMessages(List<TextMeMessage> msgs) {
        for (TextMeMessage message : msgs) {
            log.debug("@Received SMS: " + message.getMessage(), null, message.getSender());
            SMSMessage req = new SMSMessage(message.getSender(), message.getMessage(), this);
            req.put("msgID", message.getMsgID());
            req.put("time", message.getTime());
            bufferedMessages.add(req);
        }
    }

    private String executeSMSGet() throws URISyntaxException, IOException {
        URI uri = buildUrl();

        HttpClient client = new DefaultHttpClient();
        //FIXME HttpResponse httpResponse = client.execute(new HttpGet("http://localhost:8084/VirtualTextMeUg/pollsms"));
        HttpResponse httpResponse = client.execute(new HttpGet(uri));
        log.trace("@Executing URL for Server...");
        log.trace("@Url: " + uri.toString());


        StatusLine statusLine = httpResponse.getStatusLine();
        log.trace("Successfully executed");
        if (statusLine.getStatusCode() != 200) {
            log.error("@Failed To Query the SMS HTTP: Error Code: " + statusLine.getStatusCode() + " " + statusLine.getReasonPhrase() + "URL: " + uri.toString());
            return null;
        }

        HttpEntity entity = httpResponse.getEntity();

        if (entity == null) {
            log.error("@Server Replied with Invalid response for message: ");
        } else {
            log.trace("@Reading content from stream...");
            InputStream content = entity.getContent();
            log.trace("@Done done reading content. Converting Stream to string...");
            String msgs = IOUtils.toString(content);
            log.trace("@Successfuly converted stream to String with");
            return msgs;

        }
        return null;
    }

    public URI buildUrl() throws URISyntaxException {
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
        queryParams.add(new BasicNameValuePair("id", highestId + ""));
        URI uri = URIUtils.createURI(protocol, hostNameOrVariable, port, path,
                URLEncodedUtils.format(queryParams, "UTF-8"), null);
        HttpGet httpget = new HttpGet(uri);
        // System.out.println(httpget.getURI());
        return uri;
    }

    private List<TextMeMessage> parseJsonString(String executeSMSGet) {
        String jsonResponse = wrapResponseInJsonArray(executeSMSGet);
        List<TextMeMessage> newMessages = new ArrayList<TextMeMessage>();

        try {
            TextMeMessageList msgList = gs.fromJson(jsonResponse, TextMeMessageList.class);
            log.trace("@Received [" + (msgList.messages != null ? msgList.messages.size() : 0) + "] Queued Messages from the SMS Server");

            //filter out the messages that have an id > then the highest id
            for (TextMeMessage sMSMessage : msgList.messages) {
                if (sMSMessage.getIntID() > highestId) {
                    newMessages.add(sMSMessage);
                }
            }

            highestId = getHighestId(msgList.messages);
        } catch (Exception e) {
            log.error("@Problem while De-serialising json: <<" + jsonResponse + ">>", e);
        }

        return newMessages;

    }

    private static String wrapResponseInJsonArray(String str) {
        String json = "{messages : " + str + "}";
        return json;
    }

    public int getHighestId(List<TextMeMessage> msgs) {
        int newHighId = 0;
        for (TextMeMessage msg : msgs) {
            if (msg.getIntID() > highestId && msg.getIntID() > newHighId) {
                newHighId = msg.getIntID();
            }
        }
        return newHighId;
    }

    public void open() throws ChannelException {
    }

    public void close() {
    }

    public void write(Object obj, String destination) throws ChannelException {
        throw new UnsupportedOperationException("@Not supported yet.");
    }
}
