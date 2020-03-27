package org.acme.services;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;
import org.kie.kogito.process.Process;
import org.kie.kogito.process.impl.Sig;

@ApplicationScoped
public class Player {
    private static final Logger LOGGER = Logger.getLogger("Player");

    @Inject RestService service;

    @Inject
    @Named("WelcomeHome")
    Process<?> p;

    public void play(String id, String playlist) {
        service.consume(quote -> {
            var pi = p.instances().findById(id).get();
            LOGGER.info(String.format("Playlist '%s' -- Song: '%s'", playlist, quote));
            pi.send(Sig.of("painting-displayed", quote));
        });
    }
}