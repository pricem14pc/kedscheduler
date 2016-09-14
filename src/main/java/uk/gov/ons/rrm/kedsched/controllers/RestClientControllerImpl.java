package uk.gov.ons.rrm.kedsched.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.ModelAndView;

import uk.gov.ons.rrm.kedsched.dto.SurveyServiceParameters;
import uk.gov.ons.rrm.kedsched.services.KeyEventDateEnums;
import uk.gov.ons.rrm.kedsched.services.RestServiceInvoker;
import uk.gov.ons.rrm.kedsched.tasks.KeyEventDateTimerTask;

/**
 * Prototype Implementation of RestClientController to allow user to manually invoke survey-service.
 * This is primarily intended to be invoked from the client-form.jsp
 * 
 * @author pricem
 * 
 */
@RestController
public class RestClientControllerImpl implements RestClientController {

    /** The logger for this class. */
    private static final Logger LOGGER = LogManager.getLogger(RestClientControllerImpl.class);

    /** The format template for the survey service REST url. */
    private static final String SURVEY_SERVICE_URL_TEMPLATE = "%s%s/%s/%s/12/123";

    /** Scheduler instance for forcing execution of task. */
    @Autowired
    private KeyEventDateTimerTask timer;
    
    /** Service that invokes a REST endpoint. */
    @Autowired
    private RestServiceInvoker restService;
    
    /** The survey service hostname and port. */
    @Value("${ked.survey.url}")
    private String surveyServiceHost;
    
    /** The web application context of the survey service. */
    @Value("${ked.survey.url.context}")
    private String surveyServiceContext;
    
    /** Flag which indicates if any action should be taken. */
    @Value("${ked.schedule.active:false}")
    private boolean isActive;
    
    /**
     * No arg constructor.
     */
    public RestClientControllerImpl() {
        LOGGER.info("Creating RestClientControllerImpl()");
    }

    /**
     * Method to return the REST client form to the user.
     * 
     * @param request
     *            http request
     * @param response
     *            http respone
     * @return Spring MVC ModelAndView
     */
    @RequestMapping(value = "/survey-service-client", method = RequestMethod.GET)
    public final ModelAndView getSurveyServiceClient(final HttpServletRequest request, final HttpServletResponse response) {
        final ModelAndView modelAndView = new ModelAndView("client-form",
                "message", "RestClientControllerImpl :: ModelAndView getSurveyServiceClient()");
        return modelAndView;
    }

    /**
     * Method called by the REST client form to invoke the survey service endpoints.
     * @param params    The parameters entered by the user in the client form.
     * @return A string response body.
     */
    @RequestMapping(value = "/invokeEndpoint", method = RequestMethod.POST)
    @ResponseBody
    public final String invokeSurveyService(@RequestBody final SurveyServiceParameters params) {
        LOGGER.entry(params);
        
        if (!isActive) {
            return LOGGER.exit("Scheduler is not active.");
        }

        final String url = String.format(SURVEY_SERVICE_URL_TEMPLATE, surveyServiceHost, surveyServiceContext,
                params.getEndpoint(), params.getCeid());
        String result = null;

        try {
            LOGGER.info("Calling survey service with url: {}", url);
            result = restService.postForObject(url, null, String.class);
        } catch (final RestClientException ex) {
            LOGGER.error(String.format("Error invoking REST endpoint. url: %s", url), ex);
            result = ex.getMessage();
        } finally {
            LOGGER.info("result: {}", result);
        }

        return LOGGER.exit(result);
    }
    
    /** Method called by REST client form to force the scheduler to check for and execute the next task. 
     */
    @RequestMapping(value = "/forceTask", method = RequestMethod.GET)
    public final void forceTask() {
        LOGGER.debug("Executing task out of scheduled time.");
        timer.setEventID(KeyEventDateEnums.USER_PROCEDURE);
        timer.executeTask();
    }
    
}
