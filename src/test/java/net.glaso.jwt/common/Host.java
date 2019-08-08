package net.glaso.jwt.common;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention( RetentionPolicy.RUNTIME )
public @interface Host {
	public String host() default "http://localhost";
	public String port() default "8086";
}
