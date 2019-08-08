package net.glaso.jwt.framework.filter;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

public class ParsingBodyFilter  implements Filter {

	private static final Logger logger = Logger.getLogger( ParsingBodyFilter.class );
	
	@Override
	public void init( FilterConfig config ) {
		logger.info("-- ParsingBodyFilter init performed... " );
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest alteredRequest = (HttpServletRequest) request;
		if ( !alteredRequest.getMethod().equals( "GET" ) && request.getContentType() != null && request.getContentType().equals("application/json") ) {

			StringBuffer jb = new StringBuffer();
			String line;

			try ( BufferedReader body = request.getReader() ){

				while ( ( line = body.readLine() ) != null ) {
					jb.append(line);
				}

				if ( jb.toString().length() > 0 ) {
					request.setAttribute( "body", new JSONObject( jb.toString() ) );
				}
			} catch ( JSONException e ) {
				throw new JSONException( "invalid Request Body - HINT: JSON FORMAT" );
			}
		}

		chain.doFilter( request, response );
	}
}
