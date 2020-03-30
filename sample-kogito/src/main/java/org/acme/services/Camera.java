package org.acme.services;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.acme.ImageData;

@ApplicationScoped
public class Camera extends AbstractWelcomeHomeService {

    @Inject
    RestService service;

    protected static String host = "api.quotable.io";
    protected static int port = 443;
    protected static boolean ssl = true;
    protected static String endpoint = "/random";

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