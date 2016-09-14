package uk.gov.ons.rrm.kedsched.controllers;

import uk.gov.ons.rrm.kedsched.dto.SurveyServiceParameters;

/**
 * This will allow a user to manually invoke the survey-service endpoints.
 * 
 * @author pricem
 * 
 */
public interface RestClientController {

    /**
     * Process POST requests, redirecting to appropriate route.
     * @param params - JSON body of post request
     * @return result - output of request
     */
    String invokeSurveyService(SurveyServiceParameters params);

}
