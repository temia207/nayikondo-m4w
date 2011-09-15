package org.ubos.yawl.sms.service.impl;

import com.ubos.yawl.sms.utils.Settings;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.ubos.yawl.sms.service.UserService;

import com.ubos.yawl.sms.utils.SmsUser;

/**
 * 
 * @author kay
 */
public class UserServiceImpl implements UserService {

        private List<SmsUser> smsUsers;

        public boolean deletePhoneNo(String userName) {
                Settings.delete(userName);
                return false;
        }

        public boolean savePhoneNo(String userName, String phoneNo) {

                Settings.storeSetting(userName, phoneNo);
                return true;

        }

        public boolean updatePhoneNO(String userName) {
                return false;
        }

        public String getPhoneNoByUsername(String userName) {

                return Settings.readSetting(userName);

        }

        public List<SmsUser> getPhoneNoUsers() {
                smsUsers = new ArrayList<SmsUser>();
                Properties properties = Settings.properties();
                Set<Entry<Object, Object>> entrySet = properties.entrySet();

                for (Entry<Object, Object> entry : entrySet) {
                        SmsUser user = new SmsUser();
                        user.setUsername(entry.getKey().toString());
                        user.setPhoneNo(entry.getValue().toString());
                        smsUsers.add(user);
                }
                return smsUsers;
        }

        public void savePhoneNo(String oldusername, String username, String phoneNo) {
              Settings.delete(oldusername);
              Settings.storeSetting(username, phoneNo);
        }
}
