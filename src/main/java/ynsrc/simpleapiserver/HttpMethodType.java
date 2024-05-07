package ynsrc.simpleapiserver;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface HttpMethodType {
    HttpMethod value() default HttpMethod.GET;
}
