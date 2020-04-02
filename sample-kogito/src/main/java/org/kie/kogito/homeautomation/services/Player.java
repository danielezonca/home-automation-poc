package org.kie.kogito.homeautomation.services;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.kie.kogito.homeautomation.util.PostData;
import org.kie.kogito.homeautomation.util.RestService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Map;

import static io.vertx.core.json.Json.encode;
import static org.kie.kogito.homeautomation.util.RestRequest.of;

@ApplicationScoped
public class Player extends AbstractWelcomeHomeService {

    private final static String CONTENT_TYPE = "text/plain";

    @Inject
    protected RestService service;

    @ConfigProperty(name = "player.host", defaultValue = "localhost")
    protected String host;

    @ConfigProperty(name = "player.port", defaultValue = "8080")
    protected int port;

    @ConfigProperty(name = "player.ssl", defaultValue = "false")
    protected boolean ssl;

    @ConfigProperty(name = "player.endpoint")
    protected String endpoint;

    public void play(String id, String playlist, String canvas, String user) {
        LOGGER.info("Player.play");
        var messageMap = Map.of("message", formatMessage(user, playlist), "query", canvas);
        var message = encode(messageMap);
        var request = of(host, port, ssl, endpoint);
        var postData = PostData.of(message, CONTENT_TYPE);
        service.POSTRawBody(request, postData, rawQuote -> {
            LOGGER.info("Message sent");
            signalToProcess(id, "painting-displayed", message);
        });
    }

    private String formatMessage(String user, String playlist) {
        if (user.equals("unknown")) {
            return "ALARM! Unknown person in your house!";
        }
        return String.format("Welcome back %s! Playlist: %s", user, playlist);
    }
}