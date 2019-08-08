package net.glaso.jwt.framework.listener;

import net.glaso.jwt.framework.init.WasSettings;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;

public class ContextLoaderListener implements ServletContextListener {
	
	private static final Logger logger = Logger.getLogger(ContextLoaderListener.class);
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		logger.info( "run caSetting init...");
		try {
			WasSettings.init();

		} catch (IOException e) {
			logger.error( "setting files not found.");
			e.printStackTrace();
		}
	}
		
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.info("down down");
		logger.info("down down");
		logger.info("down down");
		logger.info("down down");
	}
}
