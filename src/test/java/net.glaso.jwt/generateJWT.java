package net.glaso.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.impl.JWTParser;
import com.auth0.jwt.interfaces.*;
import org.junit.Test;

import javax.xml.bind.DatatypeConverter;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class generateJWT {

    @Test
    public void test001() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance( "RSA" );
        keyPairGenerator.initialize( 2048 );
        KeyPair kPair = keyPairGenerator.generateKeyPair();

        Date currentTime = new Date();
        MessageDigest md = MessageDigest.getInstance( "SHA-256" );

        String kid = DatatypeConverter.printHexBinary( md.digest( "keyid".getBytes() ) );

        Algorithm algo = Algorithm.RSA256( (RSAPublicKey) kPair.getPublic(), (RSAPrivateKey) kPair.getPrivate() );

        Map<String, Object> jwtHeader = new HashMap<>();
        jwtHeader.put( "jwu", "https://username.auth0.com/.well-known/jwks.json" );

        String token = JWT.create()
                .withHeader( jwtHeader )
                .withKeyId( kid )
                .withIssuedAt( currentTime )
                .withSubject( "ehdvudee" )
                .withNotBefore( new Date( currentTime.getTime() ) )
                .withAudience( "cert.glaso.net" )
                .withIssuer( "jwt.glaso.net" )
                .withExpiresAt( new Date( currentTime.getTime() + 2000000000L ) )
                .withClaim( "name", "shin" )
                .withArrayClaim( "scope", new Integer[]{1, 2} )
                .sign( algo );

        System.out.println( token );


        DecodedJWT jwt = JWT.decode( token );
        System.out.println( jwt.getKeyId() );


        JWTVerifier verifier = JWT.require( algo )
                .withIssuer( "jwt.glaso.net" )
                .withAudience( "cert.glaso.net" )
                .build();

        jwt = verifier.verify( token );
        List<String> scope = jwt.getClaim( "scope" ).asList( String.class );

        System.out.println( scope.get( 1 ) );
        System.out.println( jwt.getClaim( "name" ).asString() );
        System.out.println( jwt.getHeader() );
        System.out.println( jwt.getPayload() );
        System.out.println( jwt.getSignature() );
    }

}
