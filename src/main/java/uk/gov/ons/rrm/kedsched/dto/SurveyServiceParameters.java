package uk.gov.ons.rrm.kedsched.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Represents the parameters for invoking the survey-service.
 * @author pricem
 *
 */
public class SurveyServiceParameters {

    /**
     * collection exercise ID being invoked.
     */
    private String ceid;

    /**
     * Endpoint being invoked.
     */
    private String endpoint;
    
    /**
     * Get the ceid for this survey service being invoked.
     * @return ceid for this survey service being invoked
     */
    public final String getCeid() {
        return ceid;
    }

    /**
     * Get the ceid for this survey service being invoked.
     * @return ceid for this survey service being invoked
     */
    public final String getEndpoint() {
        return endpoint;
    }

    @Override
    public final String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
    
}
