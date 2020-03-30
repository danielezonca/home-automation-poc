//
// kamel run --dev camel-k/take-picture.groovy
//

from('knative:endpoint/take-picture')
  .removeHeader('Host')
  .removeHeaders('Camel*')
  .setBody().constant(null)
  .to('https://source.unsplash.com/1600x900/?beach')
