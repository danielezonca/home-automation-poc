//
// kamel run --dev camel-k/notifier.groovy --secret=telegram
//
import org.apache.camel.component.telegram.TelegramMediaType
import org.apache.camel.component.telegram.TelegramConstants

from('knative:endpoint/notifier')
  .to('knative:endpoint/take-picture')
  .setHeader(TelegramConstants.TELEGRAM_MEDIA_TYPE).constant(TelegramMediaType.PHOTO_JPG)
  .to('telegram:bots?chatId={{telegram.chat-id}}')
  .convertBodyTo(String.class)