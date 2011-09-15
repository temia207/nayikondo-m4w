package com.google.inject.servlet;

import com.google.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
+ * The interceptor restrics some users to execute a function.
+ * @author gui guilherme.namen@gmail.com
 */
@Singleton
public class SecurityInterceptor implements MethodInterceptor {

        public Object invoke(MethodInvocation invocation) throws Throwable {
                Permission roleAnnotation = invocation.getMethod().getAnnotation(Permission.class);
                String[] roles = roleAnnotation.roles();
                String[] principals = roleAnnotation.principals();
                boolean ok = false;
                HttpServletRequest request = GuiceFilter.getRequest();
                if (roles.length == 0 && principals.length == 0) {
                        if (request.getRemoteUser() != null) {
                                ok = true;
                        }
                } else {
                        for (String role : roles) {
                                if (request.isUserInRole(role)) {
                                        ok = true;
                                        break;
                                }
                        }
                        if (!ok) {
                                for (String principal : principals) {
                                        if (request.getRemoteUser().equals(principal)) {
                                                ok = true;
                                                break;
                                        }
                                }
                        }
                }
                if (ok) {
                        return invocation.proceed();
                } else {
                        throw new SecurityException("Method invocation is not allow. The user don't have permissions");
                }
        }
}
