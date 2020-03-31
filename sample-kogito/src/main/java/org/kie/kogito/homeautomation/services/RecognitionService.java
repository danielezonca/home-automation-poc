package org.kie.kogito.homeautomation.services;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.kie.kogito.homeautomation.ImageData;
import org.kie.kogito.homeautomation.util.PostData;
import org.kie.kogito.homeautomation.util.RestService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import static org.kie.kogito.homeautomation.util.RestRequest.of;

@ApplicationScoped
public class RecognitionService extends AbstractWelcomeHomeService {

    private static final String mediaType = "image/jpeg";
    private static final String fileName = "tmp.jpg";
    private static final String name = "file";

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
        var request = of(host, port, ssl, endpoint);
        var postData = PostData.of(name, fileName, imageData.getImage(), mediaType);
        service.POST(request, postData, rawQuote -> {
            LOGGER.info("result " + rawQuote.bodyAsString());
            var json = rawQuote.bodyAsJsonObject();
            var quote = json.toString();
            var user = "evacchi";
            LOGGER.info("Recognized user: " + user + " quote: " + quote);
            signalToProcess(id, "receive-user", user);
        });
    }
}