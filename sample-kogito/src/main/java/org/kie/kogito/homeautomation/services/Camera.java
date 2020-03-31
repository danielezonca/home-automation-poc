package org.kie.kogito.homeautomation.services;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.kie.kogito.homeautomation.ImageData;
import org.kie.kogito.homeautomation.util.RestService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import static org.kie.kogito.homeautomation.util.RestRequest.of;

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
        var request = of(host, port, ssl, endpoint);
        service.GET(request, rawQuote -> {
            var json = rawQuote.bodyAsJsonObject();
            var quote = String.format("%s (%s)", json.getString("content"), json.getString("author"));
            try {
                var imageBytes = Files.readAllBytes(Paths.get("classes/test.jpg"));
                var imageData = new ImageData(quote, Base64.getEncoder().encodeToString(imageBytes));
                LOGGER.info("Received Picture " + quote);
                signalToProcess(id, "receive-picture", imageData);
            } catch (IOException e) {
                LOGGER.error(e, e);
            }
            LOGGER.info("Result " + quote);
        });
    }
}