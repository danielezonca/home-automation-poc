package org.kie.kogito.homeautomation.util;

import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.core.buffer.Buffer;
import io.vertx.mutiny.ext.web.client.HttpResponse;
import io.vertx.mutiny.ext.web.client.WebClient;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.context.ThreadContext;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.function.Consumer;

/**
 * Utility class to execute REST calls in a not-blocking way
 */
@ApplicationScoped
public class RestService {
    @Inject
    ThreadContext threadContext;
    @Inject
    ManagedExecutor managedExecutor;
    @Inject
    Vertx vertx;

    public void GET(String host, int port, boolean ssl, String endpoint, Consumer<HttpResponse<Buffer>> callback) {
        var client = initWebClient(host, port, ssl);

        threadContext.withContextCapture(client.get(endpoint).send().subscribeAsCompletionStage())
                .thenAcceptAsync(callback, managedExecutor);
    }

    public void POST(String host, int port, boolean ssl, String endpoint, Buffer body, Consumer<HttpResponse<Buffer>> callback) {
        var client = initWebClient(host, port, ssl);

        threadContext.withContextCapture(client.post(endpoint).sendBuffer(body).subscribeAsCompletionStage())
                .thenAcceptAsync(callback, managedExecutor);
    }

    protected WebClient initWebClient(String host, int port, boolean ssl) {
        return WebClient.create(
                vertx,
                new WebClientOptions()
                        .setDefaultHost(host)
                        .setDefaultPort(port)
                        .setSsl(ssl));

    }
}