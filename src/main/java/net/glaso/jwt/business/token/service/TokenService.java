package net.glaso.jwt.business.token.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import net.glaso.jwt.business.key.service.KeyService;
import net.glaso.jwt.business.user.service.UserService;
import net.glaso.jwt.business.user.vo.UserVo;
import net.glaso.jwt.framework.init.WasSettings;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {

    private final UserService userService;
    private final KeyService keyService;

    @Autowired
    public TokenService( UserService userService, KeyService keyService ) throws NoSuchAlgorithmException, InvalidKeySpecException, CertificateException {
        this.userService= userService;
        this.keyService = keyService;
    }

    public String generateToken( HttpServletRequest request ) throws NoSuchAlgorithmException, CertificateException, InvalidKeySpecException {
        JSONObject body = (JSONObject) request.getAttribute( "body" );
        String reqPw = body.getString( "password" );
        UserVo userVo = getUserInfoOne( body.getString( "id" ) );

        if ( userVo == null ) throw new IllegalArgumentException( "id or pw is invalid." );

        if ( reqPw.toLowerCase().equals( userVo.getPassword().toLowerCase() ) ) {
            return generateToken( request, userVo );
        } else {
            throw new IllegalArgumentException( "id or pw is invalid." );
        }

    }

    public String getKidFromJWT( String token ) {
        DecodedJWT jwt = JWT.decode( token );

        return jwt.getKeyId();
    }

    public DecodedJWT verifyToken( String token, String kid ) {
        Algorithm algo;
        if ( ( algo = keyService.getAlgo( kid ) ) == null ) {
            throw new NullPointerException( "kid is invalid." );
        }

        JWTVerifier verifier = JWT.require( algo )
                .withIssuer(WasSettings.getInstance().get("iss"))
                .withAudience(WasSettings.getInstance().get("aud"))
                .build();

        return verifier.verify( token );
    }

    private UserVo getUserInfoOne(String id ) {
        return this.userService.selectUserInfoOneUsingId( id );
    }

    private String generateToken( HttpServletRequest request, UserVo userVo ) throws CertificateException, NoSuchAlgorithmException, InvalidKeySpecException {
        Map<String, Object> jwtHeader = new HashMap<>();
        Date currentTime = new Date();

        String jwu = new StringBuilder( request.getScheme() )
                .append( "://" )
                .append( request.getServerName() )
                .append( ":" )
                .append( request.getServerPort() )
                .append( "/" )
                .append( keyService.getCurrentKid() ).toString();

        jwtHeader.put( "jwu", jwu );

        return JWT.create()
                .withHeader( jwtHeader )
                .withKeyId( keyService.getCurrentKid() )
                .withIssuedAt( currentTime )
                .withSubject( userVo.getId() )
                .withNotBefore( currentTime )
                .withAudience( WasSettings.getInstance().get( "aud" ) )
                .withIssuer( WasSettings.getInstance().get( "iss" ) )
                .withExpiresAt( new Date( currentTime.getTime() + Long.parseLong( WasSettings.getInstance().get( "expItvl" ) ) ) )
                .withClaim( "name", userVo.getName() )
                .withClaim( "scope", userVo.getAuthState() )
                .sign( keyService.getAlgo() );

    }


}
