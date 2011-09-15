package com.google.inject.servlet;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
+ * A annotation for restric some users to execute some functions.
+ * @author gui guilherme.namen@gmail.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Permission {

        String[] roles() default {};

        String[] principals() default {};
}
