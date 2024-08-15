package library;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigFile {
	private final Properties properties;
	private final String propertyFilePath = "Config.properties";
	private static final Logger logger = LogManager.getLogger(ConfigFile.class);

	public ConfigFile() {
		properties = new Properties();

		// Отримуємо потік ресурсів для файлу властивостей
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertyFilePath);

		// Перевіряємо, чи файл знайдено
		if (inputStream == null) {
			logger.error("Property file not found at " + propertyFilePath);
			throw new RuntimeException("Property file not found at " + propertyFilePath);
		}

		// Використовуємо try-with-resources для безпечного закриття BufferedReader
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
			properties.load(reader);
		} catch (IOException e) {
			logger.error("Failed to load properties from " + propertyFilePath, e);
			throw new RuntimeException("Failed to load properties from " + propertyFilePath, e);
		}
	}

	public String getBrowserType() {
		String browser = properties.getProperty("browser");
		if (browser != null) return browser;
		else throw new RuntimeException("browser not specified in the Config.properties file.");
	}

	public String getURL() {
		String url = properties.getProperty("url");
		if (url != null) return url;
		else throw new RuntimeException("url not specified in the Config.properties file.");
	}

	public String getDataRepositoryPath() {
		String dataRepositoryPath = properties.getProperty("dataRepositoryPath");
		if (dataRepositoryPath != null) return dataRepositoryPath;
		else throw new RuntimeException("dataRepositoryPath not specified in the Config.properties file.");
	}

	public long getWaitDuration() {
		String waitDuration = properties.getProperty("waitDuration");
		if (waitDuration != null) return Long.parseLong(waitDuration);
		else throw new RuntimeException("waitDuration not specified in the Config.properties file.");
	}
}
