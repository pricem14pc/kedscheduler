package ons.gov.uk.edc.rrm.kedsched.controllers;

public class ServiceStatus {

	private static ServiceStatus instance = null;

	private enum Status {UP}

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

}
