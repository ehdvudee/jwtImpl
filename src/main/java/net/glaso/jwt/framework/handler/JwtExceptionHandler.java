package net.glaso.jwt.framework.handler;

import com.fasterxml.jackson.core.JsonParseException;
import net.glaso.jwt.business.SampleController;
import net.glaso.jwt.business.key.controller.KeyController;
import net.glaso.jwt.business.token.controller.TokenController;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice( assignableTypes = {KeyController.class, TokenController.class, SampleController.class})
public class JwtExceptionHandler {

	// HTTP Response Code : 400
	@ExceptionHandler(value = {JsonParseException.class, IllegalAccessException.class, IllegalArgumentException.class})
	public ResponseEntity<?> numberHandler( HttpServletRequest request, Exception e ) {
		ResponseEntity<?> entity;
		Map<String, Object> entities = new HashMap<>();
		
		this.failProcess(entities, e);
		entity = new ResponseEntity<>(entities, HttpStatus.BAD_REQUEST);

		return entity;		
	}

	@ExceptionHandler(value={NullPointerException.class})
	public ResponseEntity<?> notFoundHandler( HttpServletRequest request, Exception e ) {
		ResponseEntity<?> entity;
		Map<String, Object> entities = new HashMap<>();

		this.failProcess( entities, e );
		entity = new ResponseEntity<>( entities, HttpStatus.NOT_FOUND );

		return entity;
	}

	// HTTP Response Code : 500
	// 분류되지 않는 모든 Rest 관련 Exception
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<?> defaultErrorHandler( HttpServletRequest request, Exception e ) {
		ResponseEntity<?> entity;
		Map<String, Object> entities = new HashMap<>();
		
		this.failProcess(entities, e);
		entity = new ResponseEntity<>(entities, HttpStatus.INTERNAL_SERVER_ERROR);

		return entity;
	}

	private Map<String, Object> failProcess(Map<String, Object> map, Throwable e) {
		map.put("status", "fail");
		map.put("errMsg", ExceptionUtils.getRootCauseMessage(e) );

		e.printStackTrace();

		return map;
	}
}
