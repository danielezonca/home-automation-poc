package org.kie.kogito.homeautomation.services;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.kie.kogito.homeautomation.ImageData;
import org.kie.kogito.homeautomation.util.RestService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class Camera extends AbstractWelcomeHomeService {

    @Inject
    RestService service;

    @ConfigProperty(name = "camera.host", defaultValue = "localhost")
    protected String host;

    @ConfigProperty(name = "camera.port", defaultValue = "8080")
    protected int port;

    @ConfigProperty(name = "camera.ssl", defaultValue = "false")
    protected boolean ssl;

    @ConfigProperty(name = "camera.endpoint")
    protected String endpoint;

    public void takePicture(String id) {
        LOGGER.info("Camera.takePicture");
        service.GET(host, port, ssl, endpoint, rawQuote -> {
            io.vertx.core.json.JsonObject json = rawQuote.bodyAsJsonObject();
            var quote = String.format("%s (%s)", json.getString("content"), json.getString("author"));
            var imageData = new ImageData(quote);
            LOGGER.info("Received Picture " + quote);
            signalToProcess(id, "receive-picture", imageData);
        });
    }
}