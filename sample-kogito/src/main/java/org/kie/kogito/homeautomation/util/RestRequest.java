package org.kie.kogito.homeautomation.util;

public class RestRequest {

    private final String host;
    private final int port;
    private final boolean ssl;
    private final String endpoint;

    public static RestRequest of(String host, int port, boolean ssl, String endpoint) {
        return new RestRequest(host, port, ssl, endpoint);
    }

    private RestRequest(String host, int port, boolean ssl, String endpoint) {
        this.host = host;
        this.port = port;
        this.ssl = ssl;
        this.endpoint = sanitizeEndpoint(endpoint);
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public boolean isSsl() {
        return ssl;
    }

    public String getEndpoint() {
        return endpoint;
    }

    private String sanitizeEndpoint(String rawEndpoint) {
        if (rawEndpoint == null) {
            return null;
        }
        return rawEndpoint.startsWith("/") ? rawEndpoint : "/" + rawEndpoint;
    }

    @Override
    public String toString() {
        return "http" + (ssl ? "s" : "") + "://" + host + ":" + port + endpoint;
    }
}
