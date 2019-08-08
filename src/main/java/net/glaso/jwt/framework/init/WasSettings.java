package net.glaso.jwt.framework.init;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class WasSettings {
		
	private static Map<String, String> configMap = new HashMap<String, String>();

	private final static String wasSettingsFName = "conf/jwt-token.properties";
	
	public static void init() throws IOException {
		Properties prop = new Properties();
		
		File f = new File( WasSettings.class.getClassLoader().getResource( wasSettingsFName ).getFile() );
		InputStream input = new BufferedInputStream( new FileInputStream( f ) );
		
		prop.load( input );

		WasSettings.getInstance().put( "aud", prop.getProperty( "aud" ) );
		WasSettings.getInstance().put( "iss", prop.getProperty( "iss" ) );

		long expItvl = Integer.parseInt( prop.getProperty( "exp.itvl" ) ) * 3600000L; /*1000 * 60 * 60 */
		WasSettings.getInstance().put( "expItvl", String.valueOf( expItvl ) );

		System.out.println(" ----- LOAD WAS SETTING ----- ");
		for ( Map.Entry< String, String> entry : WasSettings.getInstance().entrySet() ) {
			System.out.println( new StringBuffer().append( entry.getKey() )
					.append( " : " )
					.append( entry.getValue() ).toString() );
		}
	}
	
	public static Map<String, String> getInstance() {

		if( configMap == null ) {
			configMap = new HashMap<String, String>();
		}
		
		return configMap;
	}
}
