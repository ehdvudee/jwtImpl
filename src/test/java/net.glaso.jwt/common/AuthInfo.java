package net.glaso.jwt.common;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention( RetentionPolicy.RUNTIME )
public @interface AuthInfo {
	public String id() default "ehdvudee";
	public String pw() default "72ab994fa2eb426c051ef59cad617750bfe06d7cf6311285ff79c19c32afd236";
}
