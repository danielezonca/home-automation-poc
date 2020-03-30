package org.kie.kogito.homeautomation.services;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.kie.kogito.homeautomation.ImageData;
import org.kie.kogito.homeautomation.util.RestService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class RecognitionService extends AbstractWelcomeHomeService {

    @Inject
    RestService service;

    @ConfigProperty(name = "recognition.service.host", defaultValue = "localhost")
    protected String host;

    @ConfigProperty(name = "recognition.service.port", defaultValue = "8080")
    protected int port;

    @ConfigProperty(name = "recognition.service.ssl", defaultValue = "false")
    protected boolean ssl;

    @ConfigProperty(name = "recognition.service.endpoint")
    protected String endpoint;

    public void recognize(String id, ImageData imageData) {
        LOGGER.info("RecognitionService.recognize");
        service.GET(host, port, ssl, endpoint, rawQuote -> {
            io.vertx.core.json.JsonObject json = rawQuote.bodyAsJsonObject();
            var quote = String.format("%s (%s)", json.getString("content"), json.getString("author"));
            var user = "evacchi";
            LOGGER.info("Recognized user: " + user + " quote: " + quote);
            signalToProcess(id, "receive-user", user);
        });
    }
}