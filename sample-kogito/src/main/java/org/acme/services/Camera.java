package org.acme.services;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.web.client.WebClient;
import org.acme.ImageData;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.context.ThreadContext;
import org.jboss.logging.Logger;
import org.kie.kogito.process.Process;
import org.kie.kogito.process.impl.Sig;

@ApplicationScoped
public class Camera {

    private static final Logger LOGGER = Logger.getLogger("Camera");

    @Inject
    @Named("WelcomeHome")
    Process<?> p;

    @Inject
    RestService service;

    public void takePicture(String id) {
        service.consume(quote -> {
            var pi = p.instances().findById(id).get();
            var imageData = new ImageData(quote);
            LOGGER.info("Received Picture " + quote);
            pi.send(Sig.of("receive-picture", imageData));
        });
    }
}