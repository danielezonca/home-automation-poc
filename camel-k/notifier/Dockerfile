#
# builder
#
FROM quay.io/quarkus/ubi-quarkus-native-image:19.3.1-java11 as nativebuilder
RUN mkdir -p /tmp/ssl
RUN cp /opt/graalvm/lib/security/cacerts /tmp/ssl/

#
# app
#
FROM registry.access.redhat.com/ubi8/ubi-minimal

RUN mkdir -p /opt/camel-k
COPY --from=nativebuilder /tmp/ssl/ /opt/ssl
COPY target/home-automation-poc-camel-k-notifier-runner /opt/camel-k/runner
RUN chmod u+x /opt/camel-k/runner

ENTRYPOINT [ "/opt/camel-k/runner" , "-Djavax.net.ssl.trustStore=/opt/ssl/cacerts" ]