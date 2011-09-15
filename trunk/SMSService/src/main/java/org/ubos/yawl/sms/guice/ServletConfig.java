package org.ubos.yawl.sms.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Scopes;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.ubos.yawl.sms.servlet.DeleteServlet;
import com.ubos.yawl.sms.servlet.LoginServlet;
import com.ubos.yawl.sms.servlet.SaveServlet;
import com.ubos.yawl.sms.servlet.UpdateServlet;
import org.yawlfoundation.yawl.engine.interfce.interfaceB.InterfaceB_EnvironmentBasedServer;

/**
 *
 * @author kay
 */
public class ServletConfig extends GuiceServletContextListener {

        public static Injector injector;

        @Override
        protected Injector getInjector() {
                injector = Guice.createInjector(new ServletConfiguration(),  new SMSModule());
                return injector;
        }

        class ServletConfiguration extends ServletModule {

                @Override
                protected void configureServlets() {
                        bind(InterfaceB_EnvironmentBasedServer.class).in(Scopes.SINGLETON);
                        serve("/smsservice").with(InterfaceB_EnvironmentBasedServer.class);
                        serve("/login.htm").with(LoginServlet.class);
                        serve("/save.htm").with(SaveServlet.class);
                        serve("/update.htm").with(UpdateServlet.class);
                        serve("/delete.htm").with(DeleteServlet.class);
                }
        }


}

