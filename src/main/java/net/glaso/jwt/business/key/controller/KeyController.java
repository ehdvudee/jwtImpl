package net.glaso.jwt.business.key.controller;

import net.glaso.jwt.business.key.service.KeyService;
import net.glaso.jwt.framework.mvc.common.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/key")
public class KeyController {

    private final KeyService keyService;

    @Autowired
    public KeyController( KeyService keyService ) {
        this.keyService = keyService;
    }


    @PutMapping("updated-key")
    @Auth( authState = {Auth.AuthState.ADMIN, Auth.AuthState.SUPER_ADMIN})
    public ResponseEntity<?> updateKey(HttpServletRequest request ) {
        ResponseEntity<?> entity;
        Map<String, Object> entities = new HashMap<>();

        keyService.updateKey( request );
        entities.put( "status", "success" );
        entity = new ResponseEntity<>( entities, HttpStatus.CREATED );

        return entity;
    }

    @GetMapping("{kid}")
    @Auth( authState = {Auth.AuthState.NON_USER} )
    public ResponseEntity<?> getJku( @PathVariable("kid") String kid ) throws CertificateException, NoSuchAlgorithmException, InvalidKeySpecException {
        ResponseEntity<?> entity;
        Map<String, Object> entities = keyService.getJku( kid );

        entity = new ResponseEntity<>( entities, HttpStatus.OK );

        return entity;
    }

}
