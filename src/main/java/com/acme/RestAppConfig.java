package com.acme;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.acme.rest.filter.RequestLoggingFilter;

@Configuration
@PropertySource(value = { "classpath:application.properties" })
public class RestAppConfig {
	private Log log = LogFactory.getLog(this.getClass());

	@Autowired
	private Environment env;

	@Bean
	public FilterRegistrationBean loggingFilter() {
		// off | on
		String requestLoggingFilter = env.getProperty("request.logging.filter", "off");
		FilterRegistrationBean registration = null;
		if ("on".equals(requestLoggingFilter)) {
			registration = new FilterRegistrationBean();
			Filter filter = new RequestLoggingFilter();
			registration.setFilter(filter);
			registration.addUrlPatterns("/*");
		}
		return registration;
	}

	@PostConstruct
	public void printStartupMessage() {
		if (log.isInfoEnabled()) {
			log.info(String.format("\n\n Open in Web Browser\n\n http://localhost:%s \n", env.getProperty("server.port")));
		}
	}
}