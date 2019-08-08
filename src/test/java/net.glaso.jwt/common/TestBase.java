package net.glaso.jwt.common;

import net.glaso.jwt.common.invoker.JwtInvoker;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

@Host
@AuthInfo
public class TestBase {
	
	private String token;
	
	public String getToken() throws JSONException, IOException {
		if ( token != null ) return token; 
		
		String authTokenUri = "token";
		JSONObject body = new JSONObject();

		body.put( "id", getId() );
		body.put( "password", getPw() );

		JwtInvoker invoker = new JwtInvoker.Builder().host( getHost() )
				.uri( authTokenUri )
				.httpMethod( "POST" )
				.body( body.toString() )
				.build();
		
		JSONObject result = new JSONObject( invoker.invokeCreJwt() );
		System.out.println( result );
		
		this.token = result.getString( "token" );
		
		return token;
	}
	
	public String getHost() {
		Host hostInfo = this.getClass().getAnnotation( Host.class );	
		
		return new StringBuilder( hostInfo.host() )
				.append( ":" )
				.append( hostInfo.port() ).toString();
	}

	private String getId() {
		AuthInfo authInfo = TestBase.class.getAnnotation( AuthInfo.class );

		return authInfo.id();
	}

	private String getPw() {
		AuthInfo authInfo = TestBase.class.getAnnotation( AuthInfo.class );
		
		return authInfo.pw();
	}
}
 