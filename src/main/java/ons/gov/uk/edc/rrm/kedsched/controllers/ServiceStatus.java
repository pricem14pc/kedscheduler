package ons.gov.uk.edc.rrm.kedsched.controllers;

import java.net.UnknownHostException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServiceStatus {

	private static final Logger logger = LogManager.getLogger();
	
	private enum Status {UP}
	
	@Value("${version}")
	private String version;
	
	private final Status status = Status.UP;
	
	private final String hostName = resolveHostName();

	protected ServiceStatus() {
		logger.info("Creating ServiceStatus bean...");
	}

	private String resolveHostName() {
		
		java.net.InetAddress localMachine = null;
		
		try {
			localMachine = java.net.InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			logger.error(e);
		}
		return localMachine.getHostName();
	}
	
	public final Status getStatus() {
		return status;
	}

	public String getVersion() {
		return version;
	}

	public String getHostName() {
		return hostName;
	}
}
