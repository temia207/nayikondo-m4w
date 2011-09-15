package org.ubos.yawl.sms.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.matcher.Matchers;

import com.google.inject.servlet.Permission;
import com.google.inject.servlet.SecurityInterceptor;
import com.ubos.yawl.sms.authentication.AuthenticationService;
import com.ubos.yawl.sms.authentication.AuthenticationServiceImpl;
import org.ubos.yawl.sms.service.SMSService;
import org.ubos.yawl.sms.service.UserService;
import org.ubos.yawl.sms.service.impl.SMSServiceImpl;
import org.ubos.yawl.sms.service.impl.UserServiceImpl;
import org.ubos.yawl.sms.smsservice.SMSCustomService;

/**
 *
 * @author kay
 */
public class SMSModule extends AbstractModule {

        @Override
        protected void configure() {
                bind(UserService.class).to(UserServiceImpl.class).in(Singleton.class);
                bind(SMSService.class).to(SMSServiceImpl.class).in(Singleton.class);
                bind(SMSCustomService.class).in(Singleton.class);
                bind(AuthenticationService.class).to(AuthenticationServiceImpl.class).in(Singleton.class);
                SecurityInterceptor roleInterceptor = new SecurityInterceptor();

                bindInterceptor(Matchers.any(), Matchers.annotatedWith(Permission.class), roleInterceptor);
        }
}
