package ons.gov.uk.edc.rrm.kedsched.controllers;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.ui.ModelMap;

import ons.gov.uk.edc.rrm.kedsched.controllers.SchedulerController;

public class SchedulerControllerTest {

	SchedulerController classUnderTest = new SchedulerController();
	
	@Test
	public void displayScheduleReturnsSchedulerView() {
		String view = classUnderTest.displaySchedule(new ModelMap());
		assertEquals(view, "scheduler");
	}

}
