package net.glaso.jwt.framework.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;

public class CharacterEncodingFilter implements Filter {

	private FilterConfig config;
	private static final Logger logger = Logger.getLogger( CharacterEncodingFilter.class );
	
	@Override
	public void init( FilterConfig config ) {
		logger.info("-- CharacterEncodingFilter init performed... " );
		this.config = config;
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain nextFilter)
			throws IOException, ServletException {
		request.setCharacterEncoding( config.getInitParameter("encoding") );
		response.setCharacterEncoding( config.getInitParameter("encoding") );

	
		nextFilter.doFilter( request, response );
		
	} 

	@Override
	public void destroy() {}

}
