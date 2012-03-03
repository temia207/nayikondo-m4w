package org.m4water.server.sms;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.muk.fcit.results.sms.Channel;
import org.muk.fcit.results.sms.ChannelException;
import org.muk.fcit.results.sms.RequestListener;
import org.muk.fcit.results.sms.SMSMessage;
import org.muk.fcit.results.sms.SMSServer;
import org.muk.fcit.results.util.MLogger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author kay
 */
public class TextMeUgChannel implements Channel {

      private org.slf4j.Logger log = LoggerFactory.getLogger(TextMeUgChannel.class);
        private String hostNameOrVariable;

        private List<SMSMessage> bufferedMessages = new ArrayList<SMSMessage>();
        private Set<Integer> alreadyReceivedIds = new HashSet<Integer>();
        private String password;
        private String path;
        private int port;
        private String protocol;
        private String userName;
        private Gson gs = new Gson();

        public TextMeUgChannel() {
                port = 80;
                password = "Trip77e";
                userName = "m4w";
                path = "/mo/queryData.php";
                protocol = "http";
                hostNameOrVariable = "208.111.47.244";
        }

        public TextMeUgChannel(String hostNameOrVariable, String password, String path, int port, String protocol, String userName) {
                this.hostNameOrVariable = hostNameOrVariable;
                this.password = password;
                this.path = path;
                this.port = port;
                this.protocol = protocol;
                this.userName = userName;
        }

        public TextMeUgChannel(String userName, String password) {
                this();
                this.userName = userName;
                this.password = password;
        }

        public void open() throws ChannelException {
        }

        public SMSMessage read() throws ChannelException {
                open();
                while (bufferedMessages.isEmpty()) {
                        try {
                                waitForNewMessages();
                        } catch (Exception ex) {
                                throw new ChannelException(ex);
                        }
                }
                return bufferedMessages.remove(0);
        }

        public void write(Object obj, String destination) throws ChannelException {
                throw new UnsupportedOperationException("Not supported yet.");
        }

        public void close() {
        }

        private void waitForNewMessages() throws IOException, TimeoutException, URISyntaxException {
                List<TextMeMessage> newMsgArrivals = null;
                for (;;) {
                        try {
                                String executeSMSGet = executeSMSGet();
                                //log.trace(executeSMSGet.substring(0, 50), null, "TextMe.UG.Channel=" + this.hostNameOrVariable);
                                newMsgArrivals = parseJsonString(executeSMSGet);
				log.trace("************Got ["+newMsgArrivals.size()+"] new Arrivals");
				log.trace("************Now receive message contains ["+alreadyReceivedIds.size()+"]");
                                if (executeSMSGet == null || newMsgArrivals.isEmpty())
				{
				    log.trace("************Sleeping for One Seconds");
                                        Thread.sleep(1000);
				}
                                else
                                        break;

                        } catch (InterruptedException ex) {
                                Logger.getLogger(TextMeUgChannel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                }
                bufferTheMessages(newMsgArrivals);
        }

        private void bufferTheMessages(List<TextMeMessage> msgs) {
                for (TextMeMessage message : msgs) {
                        log.debug("=======> Received SMS: " + message.getMessage(), null, message.getSender());
                        SMSMessage req = new SMSMessage(message.getSender(), message.getMessage(), this);
                        req.put("msgID", message.getMsgID());
                        req.put("time", message.getTime());
                        bufferedMessages.add(req);
                }
        }

        private String executeSMSGet() throws URISyntaxException, IOException {
                URI uri = buildUrl();

                HttpClient client = new DefaultHttpClient();
                HttpResponse httpResponse = client.execute(new HttpGet("http://localhost:8084/VirtualTextMeUg/pollsms"));
                log.trace("************Executing URL for Server...");

                StatusLine statusLine = httpResponse.getStatusLine();
                if (statusLine.getStatusCode() != 200) {
                        log.error("Failed To Query the SMS HTTP: Error Code: " + statusLine.getStatusCode() + " " + statusLine.getReasonPhrase() + "URL: " + uri.toString());
                        return null;
                }

                HttpEntity entity = httpResponse.getEntity();

                if (entity == null) {
                        log.error("Server Replied with Invalid response for message: ");
                } else {
                        InputStream content = entity.getContent();
                        return IOUtils.toString(content);
                }
                return null;
        }

        public URI buildUrl() throws URISyntaxException {
                List<NameValuePair> qparams = new ArrayList<NameValuePair>();
                qparams.add(new BasicNameValuePair("username", userName));
                qparams.add(new BasicNameValuePair("pass", password));
                URI uri = URIUtils.createURI(protocol, hostNameOrVariable, port, path,
                        URLEncodedUtils.format(qparams, "UTF-8"), null);
                HttpGet httpget = new HttpGet(uri);
                // System.out.println(httpget.getURI());
                return uri;
        }

        private List<TextMeMessage> parseJsonString(String executeSMSGet) {
                String jsonResponse = wrapResponseInJsonArray(executeSMSGet);
                List<TextMeMessage> newMessages = new ArrayList<TextMeMessage>();

                try {
                        TextMeMessageList msgList = gs.fromJson(jsonResponse, TextMeMessageList.class);
                        for (TextMeMessage sMSMessage : msgList.messages) {
                                if (!alreadyReceivedIds.contains(sMSMessage.getIntID())) {
                                        newMessages.add(sMSMessage);
                                        alreadyReceivedIds.add(sMSMessage.getIntID());
                                }
                        }
                } catch (Exception e) {
                        log.error("Problem while Deserialising json: " + jsonResponse, e);
                }

                return newMessages;

        }

        private static String wrapResponseInJsonArray(String str) {
                String json = "{messages : " + str + "}";
                return json;
        }

        public static void main(String[] args) throws ChannelException {

                String str = "{ \"sender\":  256782157074, "
                        + " \"keyword\":  M4W, "
                        + " \"message\": \"Water pump is down\" , "
                        + " \"time\": \"13:00:00\", "
                        + " \"msgID\": \"64\" "
                        //                        + "} ";
                        + "}, "
                        + " { "
                        + " \"sender\":  25677777777, "
                        + " \"keyword\":  M4W, "
                        + " \"message\": \"Water pump is \\\"up and running\", "
                        + " \"time\": \"13:00:00\", "
                        + " \"msgID\": \"64\" "
                        + "}";

                TextMeUgChannel ch = new TextMeUgChannel();


                SMSServer smsServer = new SMSServer(ch, new RequestListener() {

                        public void processRequest(SMSMessage request) {
                                Thread currentThread = Thread.currentThread();
                                MLogger.getLogger().debug("Processing request by thread: " + currentThread.getName() + " From: " + request.getSender(), null, "textme.ug.gateway");
                                try {
                                        Thread.sleep(5000);
                                } catch (InterruptedException ex) {
                                        Logger.getLogger(TextMeUgChannel.class.getName()).log(Level.SEVERE, null, ex);
                                }
                        }
                }, 10);

                smsServer.startServer();


        }

        public String getHostNameOrVariable() {
                return hostNameOrVariable;
        }

        public void setHostNameOrVariable(String hostNameOrVariable) {
                this.hostNameOrVariable = hostNameOrVariable;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public String getPath() {
                return path;
        }

        public void setPath(String path) {
                this.path = path;
        }

        public int getPort() {
                return port;
        }

        public void setPort(int port) {
                this.port = port;
        }

        public String getProtocol() {
                return protocol;
        }

        public void setProtocol(String protocol) {
                this.protocol = protocol;
        }

        public String getUserName() {
                return userName;
        }

        public void setUserName(String userName) {
                this.userName = userName;
        }
}
