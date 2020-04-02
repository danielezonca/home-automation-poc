//
// kamel run --dev camel-k/notifier-playlist.groovy --secret=telegram
//
import org.apache.camel.component.telegram.TelegramMediaType
import org.apache.camel.component.telegram.TelegramConstants

from('knative:endpoint/notifier-playlist')
  .convertBodyTo(String.class)
  .to('telegram:bots?chatId={{telegram.chat-id}}')  
  .removeHeaders('*')
  .setBody().constant(null)
  .to('https://source.unsplash.com/1600x900/?QUERY')
  .convertBodyTo(byte[].class)
  .setHeader(TelegramConstants.TELEGRAM_MEDIA_TYPE).constant(TelegramMediaType.PHOTO_JPG)
  .to('telegram:bots?chatId={{telegram.chat-id}}')
  .convertBodyTo(String.class)
