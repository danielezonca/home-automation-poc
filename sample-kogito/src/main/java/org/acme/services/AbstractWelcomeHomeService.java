package org.acme.services;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;
import org.kie.kogito.process.Process;
import org.kie.kogito.process.impl.Sig;

@ApplicationScoped
public abstract class AbstractWelcomeHomeService {

    protected final Logger LOGGER = Logger.getLogger(this.getClass().getName());

    @Inject
    @Named("WelcomeHome")
    Process<?> p;

    protected void signalToProcess(String processId, String signalName, Object signalPayload) {
        var pi = p.instances().findById(processId).get();
        pi.send(Sig.of(signalName, signalPayload));
    }
}