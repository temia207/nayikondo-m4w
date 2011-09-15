package org.ubos.yawl.sms.service;

import java.util.List;

import com.ubos.yawl.sms.utils.SmsUser;

/**
 *
 * @author kay
 */
public interface UserService {

        public String getPhoneNoByUsername(String userName);
        public List<SmsUser> getPhoneNoUsers();
        public boolean deletePhoneNo(String userName);
        public boolean savePhoneNo(String userName, String phoneNo);
        public boolean updatePhoneNO(String userName);

        public void savePhoneNo(String oldusername, String username, String phoneNo);
}
