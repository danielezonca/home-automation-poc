//
// kamel run --dev camel-k/take-picture.groovy
//

import org.apache.camel.model.dataformat.JsonLibrary

rest {
    get {
        path '/'
        produces 'application/json'
        to 'direct:take-picture'
    }
}

from('direct:take-picture')
  .removeHeader('Host')
  .removeHeaders('Camel*')
  .setBody().constant(null)
  .to('https://raw.githubusercontent.com/danielezonca/home-automation-poc/master/testImages/index.json')
  .unmarshal().json(JsonLibrary.Jackson)
  .setHeader("images").simple('${body[images].size()}')
  .setHeader("image").simple('${body[images][${random(0,${header.images})}]}')
  .removeHeader('Host')
  .removeHeaders('Camel*')
  .setBody().constant(null)
  .toD('https://raw.githubusercontent.com/danielezonca/home-automation-poc/master/testImages/${header.image}')
