package org.kie.kogito.homeautomation.services;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.kie.kogito.homeautomation.util.RestService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class Telegram extends AbstractWelcomeHomeService {

    @Inject
    RestService service;

    @ConfigProperty(name = "telegram.host", defaultValue = "localhost")
    protected String host;

    @ConfigProperty(name = "telegram.port", defaultValue = "8080")
    protected int port;

    @ConfigProperty(name = "telegram.ssl", defaultValue = "false")
    protected boolean ssl;

    @ConfigProperty(name = "telegram.endpoint")
    protected String endpoint;

    public void send(String id, String user, String playlist) {
        LOGGER.info("Telegram.send");
        service.GET(host, port, ssl, endpoint, rawQuote -> {
            io.vertx.core.json.JsonObject json = rawQuote.bodyAsJsonObject();
            var quote = String.format("%s (%s)", json.getString("content"), json.getString("author"));
            LOGGER.info("Sending telegram: " + quote);
            signalToProcess(id, "message-sent", quote);
        });
    }
}