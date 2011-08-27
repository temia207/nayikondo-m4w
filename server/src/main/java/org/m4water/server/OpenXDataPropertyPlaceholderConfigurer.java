package org.m4water.server;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.support.ServletContextResource;

/**
 * Extends the Spring PropertyPlaceholderConfigurer
 * which allows access to variables in the OPENXDATA_SETTINGS.property file.
 *
 * @author simon@cell-life.org
 */
public class OpenXDataPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer implements
		ServletContextAware {

	private Logger log = LoggerFactory.getLogger(OpenXDataPropertyPlaceholderConfigurer.class);

	static final String SETTINGS_SUFFIX = "_SETTINGS";
	private static final String DEFAULT_CONTEXT_PATH = "/M4WATER";
	static final String PROPERTIES_FILENAME = "M4WATER_SETTINGS.properties";
	
	public Properties resolvedProps;
	
	private ServletContext servletContext;

	@Override
	protected void loadProperties(Properties props) throws IOException {
		setIgnoreResourceNotFound(true);
		super.loadProperties(props);
		
		loadContextBasedProperties(props);
		resolvedProps = props;
	}
	
	void loadContextBasedProperties(Properties props) throws IOException {
		// only attempt to load new properties if properties could not be loaded
		// from default location
		if (servletContext != null && props.isEmpty()) {
			loadFromWebappFolder(props, PROPERTIES_FILENAME);
			loadFromEnvironmentVariable(props);
		}
	}

	void loadFromEnvironmentVariable(Properties props) throws IOException {
		String contextPath = servletContext.getContextPath();
		contextPath = contextPath.length() == 0 ? DEFAULT_CONTEXT_PATH : contextPath;
		File settingsFile = getSettingsFile(contextPath);
		if (settingsFile != null){
			log.info("Attempting to load properties from: " + settingsFile.getAbsolutePath());
			setLocation(settingsFile);
			super.loadProperties(props);
		}
	}

	void loadFromWebappFolder(Properties props, String location) throws IOException {
		log.info("Attempting to load properties from servlet context path: " + location);
		setLocation(location);
		super.loadProperties(props);
	}

	File getSettingsFile(String contextPath) {
		String expectedVariableName = getEnvironmentVariableFromContextPath(contextPath);
		
		String systemVariable = System.getenv(expectedVariableName);
		if (systemVariable == null){
			systemVariable = System.getProperty(expectedVariableName);
		}
		
		File settingsFile = null;
		if (systemVariable != null) {
			settingsFile = new File(systemVariable);
		} else {
			log.warn("Unable to find environment or system variable: " + expectedVariableName);
		}
		return settingsFile;
	}

	String getEnvironmentVariableFromContextPath(String contextPath) {
		if (contextPath.startsWith("/")){
			contextPath = contextPath.substring(1);
		}
		contextPath = contextPath.toUpperCase().replaceAll("\\W", "_");
		String expectedVariableName = contextPath + SETTINGS_SUFFIX;
		return expectedVariableName;
	}

	private void setLocation(File fileLocation) {
		if (fileLocation != null){
			FileSystemResource resource = new FileSystemResource(fileLocation);
			setLocation(resource);
		}
	}
	
	private void setLocation(String location) {
		if (location != null && servletContext != null){
			ServletContextResource resource = new ServletContextResource(servletContext, location);
			setLocation(resource);
		}
	}
	
	public Properties getResolvedProps() {
		return resolvedProps;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
}
