//
// kamel run --dev camel-k/notifier-playlist.groovy --secret=telegram
//
import org.apache.camel.component.telegram.TelegramMediaType
import org.apache.camel.component.telegram.TelegramConstants
import org.apache.camel.model.dataformat.JsonLibrary

from('knative:endpoint/notifier-playlist')
  .convertBodyTo(String.class)
  .unmarshal().json(JsonLibrary.Jackson)
  .step()
    .removeHeaders('Camel*')
    .removeHeaders('Host')
    .setHeader('ImageQuery').simple('${body[query]}')
    .setBody().simple('${body[message]}')
    .to('telegram:bots?chatId={{telegram.chat-id}}')  
  .end()
  .step()  
    .removeHeaders('Camel*')
    .removeHeaders('Host')
    .setBody().constant(null)
    .toD('https://source.unsplash.com/1600x900/?${header[ImageQuery]}')
    .convertBodyTo(byte[].class)
    .setHeader(TelegramConstants.TELEGRAM_MEDIA_TYPE).constant(TelegramMediaType.PHOTO_JPG)
    .to('telegram:bots?chatId={{telegram.chat-id}}')
  .end()
  .convertBodyTo(String.class)
