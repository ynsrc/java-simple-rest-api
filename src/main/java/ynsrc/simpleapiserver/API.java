package ynsrc.simpleapiserver;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SuppressWarnings("unused")
public @interface API {

    @Retention(RetentionPolicy.RUNTIME)
    @HttpMethodType(HttpMethod.GET)

    @interface Get {
        String value() default "";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @HttpMethodType(HttpMethod.POST)
    @interface Post {
        String value() default "";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @HttpMethodType(HttpMethod.PUT)
    @interface Put {
        String value() default "";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @HttpMethodType(HttpMethod.DELETE)
    @interface Delete {
        String value() default "";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @HttpMethodType(HttpMethod.PATCH)
    @interface Patch {
        String value() default "";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @HttpMethodType(HttpMethod.HEAD)
    @interface Head {
        String value() default "";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @HttpMethodType(HttpMethod.OPTIONS)
    @interface Options {
        String value() default "";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @HttpMethodType(HttpMethod.TRACE)
    @interface Trace {
        String value() default "";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @HttpMethodType(HttpMethod.CONNECT)
    @interface Connect {
        String value() default "";
    }
}
