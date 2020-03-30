package org.kie.kogito.homeautomation.services;

import org.kie.kogito.homeautomation.ImageData;
import org.kie.kogito.homeautomation.util.RestService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class RecognitionService extends AbstractWelcomeHomeService {

    @Inject
    RestService service;

    protected static String host = "api.quotable.io";
    protected static int port = 443;
    protected static boolean ssl = true;
    protected static String endpoint = "/random";

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