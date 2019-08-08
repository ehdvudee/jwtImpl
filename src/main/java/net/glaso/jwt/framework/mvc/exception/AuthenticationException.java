package net.glaso.jwt.framework.mvc.exception;

public class AuthenticationException extends RuntimeException {

    public AuthenticationException() {
        super();
    }

    public AuthenticationException( String args ) {
        super( args );
    }

    public AuthenticationException( Exception e ) {
        super( e );
    }

    public AuthenticationException( String args, Exception e ) {
        super( args, e );
    }
}
