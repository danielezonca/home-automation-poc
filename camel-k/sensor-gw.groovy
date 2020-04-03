//
// kamel run camel-k/sensor-gw.groovy
//
rest {
    post {
        path '/sensor/{id}'
        consumes 'application/json'
        produces 'application/json'
        to 'direct:sensor'
    }
}

from('direct:sensor')
  .removeHeader('Host')
  .removeHeaders('Camel*')
  .setBody().constant(null)
  .to('log:sensor?showAll=true&multiline=true')
  .to('http://sample-kogito.cloud-sweet-cloud.apps.camel-k.rhmw-integrations.net/WelcomeHome')
