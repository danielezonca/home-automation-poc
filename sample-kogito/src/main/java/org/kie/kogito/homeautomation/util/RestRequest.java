package org.kie.kogito.homeautomation.util;

import java.util.List;
import java.util.Map;

import static java.util.Arrays.stream;
import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class RestRequest {

    private final String host;
    private final int port;
    private final boolean ssl;
    private final String endpoint;
    private final List<Map.Entry<String, String>> queryParams;

    public static RestRequest of(String host, int port, boolean ssl, String endpoint, String queryParams) {
        return new RestRequest(host, port, ssl, endpoint, parseQueryParams(queryParams));
    }

    public static RestRequest of(String host, int port, boolean ssl, String endpoint) {
        return new RestRequest(host, port, ssl, endpoint, emptyList());
    }

    private RestRequest(String host, int port, boolean ssl, String endpoint, List<Map.Entry<String, String>> queryParams) {
        this.host = host;
        this.port = port;
        this.ssl = ssl;
        this.endpoint = sanitizeEndpoint(endpoint);
        this.queryParams = unmodifiableList(queryParams);
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

    public List<Map.Entry<String, String>> getQueryParams() {
        return queryParams;
    }

    private String sanitizeEndpoint(String rawEndpoint) {
        if (rawEndpoint == null) {
            return null;
        }
        return rawEndpoint.startsWith("/") ? rawEndpoint : "/" + rawEndpoint;
    }

    protected static List<Map.Entry<String, String>> parseQueryParams(String queryParams) {
        return stream(queryParams.split("&"))
                .map(elem -> elem.split("="))
                .map(elems -> Map.entry(elems[0], elems[1]))
                .collect(toList());
    }

    @Override
    public String toString() {
        String query = this.queryParams.stream()
                .map(e -> e.getKey() + (e.getValue() == null ? "=1" : "=" + e.getValue()))
                .collect(joining(","));
        return "http" + (ssl ? "s" : "") + "://" + host + ":" + port + endpoint + "/?" + query;
    }
}
