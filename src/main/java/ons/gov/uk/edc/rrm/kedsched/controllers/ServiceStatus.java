package ons.gov.uk.edc.rrm.kedsched.controllers;

import org.springframework.beans.factory.annotation.Value;

public class ServiceStatus {

	private static ServiceStatus instance = null;

	private enum Status {UP}
	
	@Value(value = "{$kedscheduler-version}")
	private String version;
	
	private final Status status = Status.UP;

	protected ServiceStatus() {
	}

	public static ServiceStatus getInstance() {
		if (instance == null) {
			instance = new ServiceStatus();
		}
		return instance;
	}

	public final Status getStatus() {
		return status;
	}

	public String getVersion() {
		return version;
	}
}
