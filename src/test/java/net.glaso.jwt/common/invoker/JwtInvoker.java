package net.glaso.jwt.common.invoker;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class JwtInvoker {
	private final String host;
	private final String uri;
	private final String httpMethod;
	private final HttpInvoker httpInvoker;
	
	private String body;
	private String authToken = null;
	
	private JwtInvoker(Builder builder ) {
		this.host = builder.host;
		this.uri = builder.uri;
		this.httpMethod = builder.httpMethod;
		this.body = builder.body;
		this.httpInvoker = new HttpInvoker();

		if ( builder.authToken != null ) authToken = builder.authToken;
	}

	public String invokeAuthorizedJwt() throws IOException {
		HttpURLConnection conn = (HttpURLConnection) initUrl( host, uri ).openConnection();
		conn.setRequestProperty("x-access-token", authToken );
		
		return httpInvoker.invokeKMS( conn, httpMethod, body );
	}
	
	public String invokeCreJwt() throws IOException {
		HttpURLConnection conn = (HttpURLConnection) initUrl( host, uri ).openConnection();
		
		return httpInvoker.invokeKMS( conn, httpMethod, body );
	}
	
	private URL initUrl( String host, String uri ) throws IOException {
		return new URL( new StringBuilder( host )
				.append( "/" )
				.append( uri ).toString() );
	}
	
	public static class Builder {

		private String host;
		private String uri;
		private String httpMethod;
		
		private String body;
		private String authToken;

		public Builder host( String host ) { this.host = host; return this; }
		public Builder uri( String uri ) { this.uri = uri; return this; }
		public Builder httpMethod( String httpMethod ) { this.httpMethod = httpMethod; return this; } 
		public Builder body( String body ) { this.body = body; return this; }
		public Builder authToken( String token ) { this.authToken = token; return this; } 

		public JwtInvoker build() {
			if ( host == null || uri == null || httpMethod == null ) 
				throw new NullPointerException( "host, uri or httpMethod is null." );
			
			return new JwtInvoker( this );
		}
	}
}
