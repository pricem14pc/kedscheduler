package uk.gov.ons.rrm.kedsched.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.reflect.Whitebox;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RunWith(MockitoJUnitRunner.class)
public class RestServiceInvokerImplTest {
    
    @Mock
    private RestTemplate restTemplate;
    
    RestServiceInvokerImpl rest;
    
    @Before
    public void setUp() {
        rest = new RestServiceInvokerImpl();
        
        Whitebox.setInternalState(rest, "restTemplate", restTemplate);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testPostForObjectRetunsStringResult() {
        final String url = "some url";
        final String request = null;
        Mockito.when(restTemplate.postForObject(Mockito.anyString(), Mockito.any(Object.class), Mockito.any(Class.class))).thenReturn("true");
        
        
        final String result = rest.postForObject(url, request, String.class);
        
        Assert.assertNotNull(result);
        Mockito.verify(restTemplate).postForObject(url, request, String.class);
    }
    
    @SuppressWarnings("unchecked")
    @Test(expected = RestClientException.class)
    public void testPostForObjectThrowsExceptionOnError() {
        Mockito.when(restTemplate.postForObject(Mockito.anyString(), Mockito.any(Object.class), Mockito.any(Class.class))).thenThrow(RestClientException.class);
        
        rest.postForObject(null, null, String.class);
    }
    
    
}