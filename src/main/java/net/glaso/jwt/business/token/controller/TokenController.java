package net.glaso.jwt.business.token.controller;

import net.glaso.jwt.business.token.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/token")
public class TokenController {

    private final TokenService tokenService;

    @Autowired
    public TokenController( TokenService tokenService ) {
        this.tokenService = tokenService;
    }

    @PostMapping("")
    public ResponseEntity<?> generateToken(HttpServletRequest request ) throws InvalidKeyException, IllegalAccessException, NoSuchAlgorithmException, CertificateException, NoSuchProviderException, SignatureException, IOException, InvalidKeySpecException {
        ResponseEntity<?> entity;
        Map<String, Object> entities = new HashMap<>();

        entities.put( "token", tokenService.generateToken( request ) );
        entities.put( "status", "success" );
        entity = new ResponseEntity<>( entities, HttpStatus.CREATED );

        return entity;
    }
}
