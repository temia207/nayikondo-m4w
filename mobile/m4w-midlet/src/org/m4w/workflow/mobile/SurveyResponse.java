/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.m4w.workflow.mobile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.openxdata.db.util.Persistent;
import org.openxdata.db.util.PersistentHelper;

/**
 *
 * @author kay
 */
public class SurveyResponse implements Persistent {

        private byte status;
        private String message;

        public void write(DataOutputStream stream) throws IOException {
                stream.write(status);
                PersistentHelper.writeUTF(stream, message);
        }

        public void read(DataInputStream stream) throws IOException, InstantiationException, IllegalAccessException {
                status = stream.readByte();
                message = PersistentHelper.readUTF(stream);
        }

        public boolean isSuccess(){
                return status == 1;
        }

        public String getMessage() {
                return message;
        }

}
