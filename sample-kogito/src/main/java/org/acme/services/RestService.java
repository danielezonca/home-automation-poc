package org.acme.services;

import java.util.function.Consumer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.web.client.WebClient;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.context.ThreadContext;
import org.kie.kogito.process.Process;

@ApplicationScoped
public class RestService {
    @Inject ThreadContext threadContext;
    @Inject ManagedExecutor managedExecutor;
    @Inject Vertx vertx;

    @Inject
    @Named("WelcomeHome")
    Process<?> p;

    public void consume(Consumer<String> consumer) {
        var client = WebClient.create(
                vertx,
                new WebClientOptions()
                        .setDefaultHost("api.quotable.io")
                        .setDefaultPort(443)
                        .setSsl(true));

        threadContext.withContextCapture(client.get("/random").send().subscribeAsCompletionStage())
                .thenAcceptAsync(response -> {
                    io.vertx.core.json.JsonObject json = response.bodyAsJsonObject();
                    var quote = String.format("%s (%s)", json.getString("content"), json.getString("author"));
                    consumer.accept(quote);
                }, managedExecutor);
    }
}