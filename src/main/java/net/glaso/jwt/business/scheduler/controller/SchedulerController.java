package net.glaso.jwt.business.scheduler.controller;

import net.glaso.jwt.business.scheduler.service.UpdateJwtKeyScheduler;
import net.glaso.jwt.framework.mvc.common.Auth;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("scheduler")
@RestController
public class SchedulerController {

    private final UpdateJwtKeyScheduler updateJwtKeyScheduler;
    public SchedulerController( UpdateJwtKeyScheduler sch ) {
        this.updateJwtKeyScheduler = sch;
    }

    @GetMapping
    @Auth( authState = {Auth.AuthState.ADMIN, Auth.AuthState.SUPER_ADMIN})
    public ResponseEntity<?> updateKeyTest() {
        ResponseEntity<?> entity;
        Map<String, Object> entities = new HashMap<>();

        updateJwtKeyScheduler.runner().run();
        entities.put( "status", "success" );
        entity = new ResponseEntity<>( entities, HttpStatus.OK );

        return entity;
    }




}
