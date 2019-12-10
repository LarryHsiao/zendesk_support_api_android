package com.larryhsiao.zendesk_sample;

import com.google.gson.JsonObject;
import com.silverhetch.clotho.Action;
import okhttp3.*;

import java.io.IOException;

/**
 * Action to create new ticket to zendesk.
 */
public class NewTicket implements Action {
    private final Config config;
    private final String title;
    private final String conent;

    public NewTicket(Config config, String title, String conent) {
        this.config = config;
        this.title = title;
        this.conent = conent;
    }

    @Override
    public void fire() {
        try {
            final OkHttpClient client = new OkHttpClient.Builder().build();
            Response res = client.newCall(new Request.Builder()
                .url(config.zendeskHost() + "/api/v2/tickets.json")
                .method("POST", RequestBody.create(
                    jsonBody(), MediaType.parse("application/json")
                ))
                .addHeader("Authorization", "Basic " + config.authKey())
                .build()
            ).execute();
            if (res.code() >= 400) {
                throw new RuntimeException("Request failed code: " + res.code());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String jsonBody() {
        final JsonObject comment = new JsonObject();
        comment.addProperty("body", conent);
        final JsonObject ticket = new JsonObject();
        ticket.addProperty("subject", title);
        ticket.add("comment", comment);
        final JsonObject root = new JsonObject();
        root.add("ticket", ticket);
        return root.toString();
    }
}
