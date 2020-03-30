package org.kie.kogito.homeautomation.services;

import org.kie.kogito.homeautomation.util.RestService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class Player extends AbstractWelcomeHomeService {

    @Inject
    RestService service;

    protected static String host = "api.quotable.io";
    protected static int port = 443;
    protected static boolean ssl = true;
    protected static String endpoint = "/random";

    public void play(String id, String playlist) {
        LOGGER.info("Player.play");
        service.GET(host, port, ssl, endpoint, rawQuote -> {
            io.vertx.core.json.JsonObject json = rawQuote.bodyAsJsonObject();
            var quote = String.format("%s (%s)", json.getString("content"), json.getString("author"));
            LOGGER.info(String.format("Playlist '%s' -- Song: '%s'", playlist, quote));
            signalToProcess(id, "painting-displayed", quote);
        });
    }
}