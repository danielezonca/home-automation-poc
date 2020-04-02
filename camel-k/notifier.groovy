//
// kamel run --dev camel-k/notifier.groovy --secret=telegram
//
import org.apache.camel.component.telegram.TelegramMediaType
import org.apache.camel.component.telegram.TelegramConstants

from('knative:endpoint/notifier')
  .removeHeaders('*')
  .setBody().constant(null)
  .to('https://source.unsplash.com/1600x900/?beach')
  .convertBodyTo(byte[].class)
  .setHeader(TelegramConstants.TELEGRAM_MEDIA_TYPE).constant(TelegramMediaType.PHOTO_JPG)
  .to('telegram:bots?chatId={{telegram.chat-id}}')
  .convertBodyTo(String.class)