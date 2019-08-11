package net.glaso.jwt.framework.interceptor;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import net.glaso.jwt.business.token.common.TokenConstants;
import net.glaso.jwt.business.token.service.TokenService;
import net.glaso.jwt.framework.mvc.common.Auth;
import net.glaso.jwt.framework.mvc.exception.AuthenticationException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthInterceptor extends HandlerInterceptorAdapter {

    @Inject
    TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String token = request.getHeader( TokenConstants.TOKEN_HEADER );

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Auth auth = handlerMethod.getMethodAnnotation( Auth.class );

        int targetBitMask = 0;
        for ( Auth.AuthState enumAuth : auth.authState() ) {
            targetBitMask = targetBitMask + enumAuth.getAuthState();
        }

        if ( targetBitMask == Auth.AuthState.NON_USER.getAuthState() ) {
            return true;
        }

        String kid;
        try {
            kid = tokenService.getKidFromJWT(token);
        } catch( NullPointerException e ) {
            throw new IllegalArgumentException( "token is invalid." );
        }

        DecodedJWT jwt;
        try {
            jwt = tokenService.verifyToken( token, kid );
        } catch ( JWTVerificationException e ) {
            throw new AuthenticationException( "token is invalid.");
        }

        int reqBitMask = jwt.getClaim( "scope" ).asInt();
        if ( (  reqBitMask & targetBitMask ) == reqBitMask )
            return true;
        else {
            throw new AuthenticationException( "token authorization is invalid.");
        }


    }

}
