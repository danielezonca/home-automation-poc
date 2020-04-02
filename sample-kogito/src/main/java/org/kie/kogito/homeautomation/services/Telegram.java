package org.kie.kogito.homeautomation.services;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.kie.kogito.homeautomation.ImageData;
import org.kie.kogito.homeautomation.util.PostData;
import org.kie.kogito.homeautomation.util.RestService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import static org.kie.kogito.homeautomation.util.RestRequest.of;

import java.util.Base64;

@ApplicationScoped
public class Telegram extends AbstractWelcomeHomeService {

    private static final String CONTENT_TYPE = "image/jpeg";

    @Inject
    protected RestService service;

    @ConfigProperty(name = "telegram.host", defaultValue = "localhost")
    protected String host;

    @ConfigProperty(name = "telegram.port", defaultValue = "8080")
    protected int port;

    @ConfigProperty(name = "telegram.ssl", defaultValue = "false")
    protected boolean ssl;

    @ConfigProperty(name = "telegram.endpoint")
    protected String endpoint;

    public void send(String id, ImageData imageData, String user) {
        LOGGER.info("Telegram.send");
        if(!user.equals("unknown")) {
            LOGGER.info("No alarm");
            signalToProcess(id, "message-sent", "Message sent");
        } else {
            sendUnknownPicture(id, imageData.getImage());
        }
    }

    private void sendUnknownPicture(String id, String image) {
        var request = of(host, port, ssl, endpoint);
        var content = Base64.getDecoder().decode(image);
        var postData = PostData.of(content, CONTENT_TYPE);
        service.POSTRawBody(request, postData, rawQuote -> {
            LOGGER.info("Message sent");
            signalToProcess(id, "message-sent", "Message sent");
        });
    }
}