package org.acme.services;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.acme.ImageData;
import org.jboss.logging.Logger;
import org.kie.kogito.process.Process;
import org.kie.kogito.process.impl.Sig;

@ApplicationScoped
public class RecognitionService {
    private static final Logger LOGGER = Logger.getLogger("RecognitionService");

    @Inject
    @Named("WelcomeHome")
    Process<?> p;

    @Inject
    RestService service;

    public void recognize(String id, ImageData imageData) {
        service.consume(quote -> {
            var pi = p.instances().findById(id).get();
            var user = "evacchi";
            LOGGER.info("Recognized user: " + user);
            pi.send(Sig.of("receive-user", user));
        });
    }
}