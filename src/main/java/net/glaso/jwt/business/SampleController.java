package net.glaso.jwt.business;

import net.glaso.jwt.framework.mvc.common.Auth;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sample")
public class SampleController {

    private static Map<String, String> sampleMap;

    public SampleController() {
        sampleMap = new HashMap<>();
    }

    @PostMapping
    @Auth( authState = {Auth.AuthState.ADMIN, Auth.AuthState.SUPER_ADMIN, Auth.AuthState.DEVEL_USER, Auth.AuthState.COMMON_USER})
    public ResponseEntity<?> genVal( HttpServletRequest request ) {
        ResponseEntity<?> entity;
        Map<String, Object> entities = new HashMap<>();
        JSONObject body = (JSONObject) request.getAttribute( "body" );

        String id = body.getString( "id" );

        if ( id == null ) throw new IllegalArgumentException( "input your id." );
        sampleMap.put( id, body.toString() );

        entities.put( "status", "success" );
        entity = new ResponseEntity<>(entities, HttpStatus.OK);

        return entity;
    }

    @GetMapping("{id}")
    @Auth( authState = {Auth.AuthState.ADMIN, Auth.AuthState.SUPER_ADMIN, Auth.AuthState.DEVEL_USER, Auth.AuthState.COMMON_USER})
    public ResponseEntity<?> getVal(@PathVariable("id") String id, HttpServletRequest request ) {
        ResponseEntity<?> entity;
        Map<String, Object> entities = new HashMap<>();

        entities.put( "data", sampleMap.get( id ) );
        entities.put( "status", "success" );
        entity = new ResponseEntity<>(entities, HttpStatus.OK);

        return entity;
    }

    @PutMapping("{id}")
    @Auth( authState = {Auth.AuthState.ADMIN, Auth.AuthState.SUPER_ADMIN })
    public ResponseEntity<?> putVal(@PathVariable("id") String id, HttpServletRequest request ) {
        ResponseEntity<?> entity;
        Map<String, Object> entities = new HashMap<>();
        JSONObject body = (JSONObject) request.getAttribute( "body" );

        if ( id == null ) throw new IllegalArgumentException( "input your id." );
        sampleMap.put( id, body.toString() );

        entities.put( "status", "success" );
        entity = new ResponseEntity<>(entities, HttpStatus.OK);

        return entity;
    }

    @DeleteMapping("{id}")
    @Auth( authState = {Auth.AuthState.ADMIN, Auth.AuthState.SUPER_ADMIN })
    public ResponseEntity<?> deleteVal(@PathVariable("id") String id, HttpServletRequest request ) {
        ResponseEntity<?> entity;
        Map<String, Object> entities = new HashMap<>();

        sampleMap.put( id, null );
        entities.put( "status", "success" );
        entity = new ResponseEntity<>(entities, HttpStatus.OK);

        return entity;
    }


}
