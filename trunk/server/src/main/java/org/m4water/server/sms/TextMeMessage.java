package org.m4water.server.sms;

/**
 *
 * @author kay
 */
public class TextMeMessage {
//        {
//  “sender”:  25677777777,
//  “keyword”:  M4W,
//  “message”: Water pump is up and running,
//  “time”: 13:00:00,
//  “msgID”: 64
//
//}

        private String sender;
        private String keyword;
        private String message;
        private String time;
        private String msgID;
        private int msgInID = -1;

        public TextMeMessage() {
        }

        public String getKeyword() {
                return keyword;
        }

        public void setKeyword(String keyword) {
                this.keyword = keyword;
        }

        public String getMessage() {
                return message;
        }

        public void setMessage(String message) {
                this.message = message;
        }

        public String getMsgID() {
                return msgID;
        }

        public void setMsgID(String msgID) {
                this.msgID = msgID;
        }

        public String getSender() {
                return sender;
        }

        public void setSender(String sender) {
                this.sender = sender;
        }

        public String getTime() {
                return time;
        }

        public void setTime(String time) {
                this.time = time;
        }

        public int getIntID() {
                if (msgInID == -1) {
                        msgInID = Integer.parseInt(getMsgID());
                }
                return msgInID;
        }
}
