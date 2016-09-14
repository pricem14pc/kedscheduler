package uk.gov.ons.rrm.kedsched.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;

import uk.gov.ons.rrm.kedsched.dto.ServiceStatus;
import uk.gov.ons.rrm.kedsched.dto.ServiceStatusImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.management.*"})
public class StatusControllerImplTest {
        
    
    ServiceStatus serviceStatus = new ServiceStatusImpl("0.0.1-SNAPSHOT","ked-scheduler");
    
    @InjectMocks
    StatusController statusController = new StatusControllerImpl(serviceStatus);
          
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    
    @Test
    public void testServiceStatusIsReturned() {
        ServiceStatus status = statusController.status(); 
        assertEquals(this.serviceStatus, status);
    }
    
    @Test
    public void testServiceStatusIsValid() throws JsonProcessingException, IOException {
        
        ServiceStatus serviceStatus = statusController.status();
        
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(serviceStatus.toString());
        
        JsonNode nodeVersion = json.get("version");
        assertEquals(nodeVersion.textValue(),"0.0.1-SNAPSHOT");
        
        JsonNode nodeName = json.get("name");
        assertEquals(nodeName.textValue(),"ked-scheduler");
        
        JsonNode nodeStatus = json.get("status");
        assertEquals(nodeStatus.textValue(),"UP");
        
        JsonNode nodeUpDate = json.get("upDate");
        assertTrue(nodeUpDate.textValue().length() > 15);
        
        JsonNode nodeHostname = json.get("hostName");
        assertTrue(nodeHostname.textValue().length() > 2);
    }   
}
