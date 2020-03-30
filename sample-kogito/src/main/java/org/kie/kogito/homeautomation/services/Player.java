package org.kie.kogito.homeautomation.services;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.kie.kogito.homeautomation.util.RestService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class Player extends AbstractWelcomeHomeService {

    @Inject
    RestService service;

    @ConfigProperty(name = "player.host", defaultValue = "localhost")
    protected String host;

    @ConfigProperty(name = "player.port", defaultValue = "8080")
    protected int port;

    @ConfigProperty(name = "player.ssl", defaultValue = "false")
    protected boolean ssl;

    @ConfigProperty(name = "player.endpoint")
    protected String endpoint;

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