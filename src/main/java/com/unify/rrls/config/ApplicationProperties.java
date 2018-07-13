package com.unify.rrls.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to JHipster.
 * <p>
 * Properties are configured in the application.yml file.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
	 public String mailFromName;

	public String getMailFromName() {
		return mailFromName;
	}

	public void setMailFromName(String mailFromName) {
		this.mailFromName = mailFromName;
	}

}
