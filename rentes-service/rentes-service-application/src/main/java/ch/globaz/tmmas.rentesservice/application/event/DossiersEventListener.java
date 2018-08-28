package ch.globaz.tmmas.rentesservice.application.event;



import ch.globaz.tmmas.rentesservice.domain.event.DossierCreeEvent;
import ch.globaz.tmmas.rentesservice.domain.notification.NotificationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DossiersEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(DossiersEventListener.class);



    @Autowired
    ObjectMapper mapper;

    @EventListener
    void rentesCreesEvent(DossierCreeEvent event) throws JsonProcessingException {

        LOGGER.info("RenteCree Event {}",event);


    }
}
