//
// kamel run --dev camel-k/sensor-gw.groovy
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
  .to('log:sensor?showAll=true&multiline=true')
  .to('knative:endpoint/take-picture')
  .to('knative:endpoint/notifier')