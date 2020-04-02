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
        service.GET(request, httpResponse -> {
            try {
                // Enable service
                var imageBytes = httpResponse.body().getBytes();
                // var imageBytes = Files.readAllBytes(Paths.get("../../testImages/bradPitt_3.jpg"));
                // var imageBytes = Files.readAllBytes(Paths.get("../../testImages/angelinaJolie_3.jpg"));
                // var imageBytes = Files.readAllBytes(Paths.get("../../testImages/nicolasCage_3.jpg"));
                // var imageBytes = Files.readAllBytes(Paths.get("../../testImages/jackNicholson_1.jpg"));
                var imageAsBase64 = Base64.getEncoder().encodeToString(imageBytes);
                var imageData = new ImageData(imageAsBase64);
                signalToProcess(id, "receive-picture", imageData);
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        });
    }
}