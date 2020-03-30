package org.acme.services;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class Telegram extends AbstractWelcomeHomeService {

    @Inject
    RestService service;

    protected static String host = "api.quotable.io";
    protected static int port = 443;
    protected static boolean ssl = true;
    protected static String endpoint = "/random";

    public void send(String id, String user, String playlist) {
        LOGGER.info("Telegram.send");
        service.GET(host, port, ssl, endpoint, rawQuote -> {
            io.vertx.core.json.JsonObject json = rawQuote.bodyAsJsonObject();
            var quote = String.format("%s (%s)", json.getString("content"), json.getString("author"));
            LOGGER.info("Sending telegram: "+quote);
            signalToProcess(id, "message-sent", quote);
        });
    }
}