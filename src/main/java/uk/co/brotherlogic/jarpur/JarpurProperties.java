package uk.co.brotherlogic.jarpur;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContext;

public class JarpurProperties {
	private static Properties properties;

	public static void set(ServletContext context) {
		buildProperties(context);
	}

	public static String get(String key) {
		if (properties == null)
			buildProperties(null);
		if (properties != null)
			return properties.getProperty(key);
		else
			return "NULL";
	}

	private static void buildProperties(ServletContext context) {
		properties = new Properties();
		try {
			if (context != null) {
				properties.load(new FileInputStream(new File(context
						.getRealPath("WEB-INF/props/web.properties"))));
				System.err.println("LOADED");
			} else
				properties.load(new FileInputStream(new File(
						"config/web.properties")));
		} catch (IOException e) {
			// Try to read the file from within the war
			e.printStackTrace();
			properties = null;
		}
	}
}
