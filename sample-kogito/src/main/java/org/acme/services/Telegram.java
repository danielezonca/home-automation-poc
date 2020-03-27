package org.acme.services;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;
import org.kie.kogito.process.Process;
import org.kie.kogito.process.impl.Sig;

@ApplicationScoped
public class Telegram {
    private static final Logger LOGGER = Logger.getLogger("Telegram");

    @Inject
    @Named("WelcomeHome")
    Process<?> p;

    @Inject
    RestService service;

    public void send(String id, String user, String playlist) {
        service.consume(quote -> {
            var pi = p.instances().findById(id).get();
            LOGGER.info("Sending telegram: "+quote);
            pi.send(Sig.of("message-sent", quote));
        });
    }
}