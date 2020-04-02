//
// kamel run --dev camel-k/notifier-playlist.groovy --secret=telegram
//
import org.apache.camel.component.telegram.TelegramMediaType
import org.apache.camel.component.telegram.TelegramConstants

from('knative:endpoint/notifier-playlist')
  .convertBodyTo(String.class)
  .to('telegram:bots?chatId={{telegram.chat-id}}')
  .convertBodyTo(String.class)