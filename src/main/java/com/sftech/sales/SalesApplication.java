package com.sftech.sales;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class SalesApplication {

	private static final Logger logger = LoggerFactory.getLogger(SalesApplication.class);

	public static void main(String[] args) {
		var app = SpringApplication.run(SalesApplication.class, args);
		Environment env = app.getEnvironment();

		String protocol = "http";
		if (env.getProperty("server.ssl.key-store") != null) {
			protocol = "https";
		}

		String port = env.getProperty("server.port", "8080");
		String contextPath = env.getProperty("server.servlet.context-path", "");

		logger.info("\n----------------------------------------------------------\n\t" +
				"Application '{}' is running! Access URLs:\n\t" +
				"Local: \t\t{}://localhost:{}{}\n\t" +
				// "External: \t{}://{}:{}{}\n\t" +
				"Swagger UI: \t{}://localhost:{}{}/swagger-ui.html\n\t" +
				"Profile(s): \t{}\n----------------------------------------------------------",
				env.getProperty("spring.application.name"),
				protocol, port, contextPath,
				protocol, port, contextPath,
				env.getActiveProfiles());
	}
}
