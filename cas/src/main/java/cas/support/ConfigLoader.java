package cas.support;

import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {
	private ConfigLoader() {}

	private static Properties properties = new Properties();
	
	static {
		try {
			properties.load(ConfigLoader.class.getResourceAsStream("/cas-config.properties"));
		} catch (IOException e) {
			throw new RuntimeException("load config error", e);
		}
	}
	
	public static String getConfig(String key) {
		return properties.getProperty(key);
	}
}