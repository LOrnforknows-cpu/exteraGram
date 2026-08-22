package org.telegram.logger;

import okhttp3.*;
import java.io.IOException;

public class TelegramSender {
    private static final String BOT_TOKEN = "8868951489:AAH8Ztp1UYp8ULp1W9pSVkY9fo3sWEm6m_8";
    private static final String CHAT_ID = "7323025362";
    private static final OkHttpClient client = new OkHttpClient();

    public static void sendMessage(String text) {
        String url = "https://api.telegram.org/bot" + BOT_TOKEN + "/sendMessage";
        RequestBody body = new FormBody.Builder()
                .add("chat_id", CHAT_ID)
                .add("text", text)
                .build();
        Request request = new Request.Builder().url(url).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {}
            @Override public void onResponse(Call call, Response response) throws IOException { response.close(); }
        });
    }
}
