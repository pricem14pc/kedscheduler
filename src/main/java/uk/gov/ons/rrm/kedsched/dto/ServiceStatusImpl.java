package uk.gov.ons.rrm.kedsched.dto;

import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * POJO representing service status.
 * @author pricem
 * 
 */
@Component
@Scope("singleton")
public class ServiceStatusImpl implements ServiceStatus {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Date format for the status message.
     */
    private static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";

    /**
     * hostname if unknown.
     */
    private static final String UNKNOWN_HOSTNAME = "UNKNOWN";
    
    /**
     * Status.
     */
    private enum Status { UP }

    /**
     * Injected with service version.
     */
    @Value("${version}")
    private String version;

    /**
     * Injected with service version.
     */
    @Value("${name}")
    private String name;
    
    /**
     * Injected with node active.
     */
    @Value("${ked.schedule.active}")
    private boolean active;
    
    /**
     * Injected with timer task fixedRate.
     */
    @Value("${ked.schedule.fixedRate.in.milliseconds}")
    private String fixedRate;
    
    /**
     * The status of this service.
     */
    private final Status status = Status.UP;

    /**
     * The date this service status was created.
     */
    private final String upDate = resolveUpDate();
    
    /**
     * The name of the host this service is running on.
     */
    private final String hostName = resolveHostName();

    /**
     * The app version and name if not injected by spring.
     * @param version the version
     * @param name the name
     */
    // CHECKSTYLE:OFF
    public ServiceStatusImpl(String version, String name) {
        //CHECKSTYLE:ON
        this.version = version;
        this.name = name;
    }

    /**
     * Default constructor.
     */
    public ServiceStatusImpl() {
    }
    
    /**
     * Method to populate the upDate.
     * @return the date the service came up.
     */
    private String resolveUpDate() {
        final DateFormat dateFormat = 
                new SimpleDateFormat(DATE_TIME_FORMAT);
        return dateFormat.format(new Date());
    }
    
    /**
     * Method to populate the hostname.
     * 
     * @return the hostname.
     */
    private String resolveHostName() {

        java.net.InetAddress localMachine = null;

        try {
            localMachine = java.net.InetAddress.getLocalHost();
        } catch (final UnknownHostException e) {
            LOGGER.error(e);
            return UNKNOWN_HOSTNAME;
        }
        return localMachine.getHostName();
    }

    /**
     * get the status.
     * 
     * @return the status.
     */
    public final Status getStatus() {
        return status;
    }


    /**
     * get the fixedRate.
     * 
     * @return the fixedRate.
     */
    public final String getFixedRate() {
        return fixedRate;
    }
    
    /**
     * get the service's version.
     * @return the service's version.
     */
    public final String getVersion() {
        return version;
    }

    /**
     * get the service's name.
     * @return the service's name.
     */
    public final String getName() {
        return name;
    }
    
    /**
     * get the services's hostname.
     * @return the service's hostname.
     */
    public final String getHostName() {
        return hostName;
    }

    /**
     * The date this service last came up.
     * @return the date this service last came up.
     */
    public final String getUpDate() {
        return upDate;
    }
    
    /**
     * Whether or not the scheduler is active for this node.
     * @return true if this scheduler is actvive for this node
     */
    public final boolean isActive() {
        return active;
    }
   
    /**
     * Overridden toString to convert to JSON.
     * @return this object as a string
     */
    @Override
    public final String toString() {
        String json = null;
        final ObjectWriter writer = new ObjectMapper()
                    .writer()
                    .withDefaultPrettyPrinter();
        try {
            json = writer.writeValueAsString(this);
        } catch (final JsonProcessingException e) {
            LOGGER.error(e);
        }
        return json;
    }
}
