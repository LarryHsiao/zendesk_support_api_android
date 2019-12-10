package com.larryhsiao.zendesk_sample;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.silverhetch.clotho.Source;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Source to list all tickets.
 */
public class Tickets implements Source<List<Ticket>> {
    private final Config config;

    public Tickets(Config config) {
        this.config = config;
    }

    @Override
    public List<Ticket> value() {
        try {
            final OkHttpClient client = new OkHttpClient.Builder().build();
            Response res = client.newCall(new Request.Builder()
                .url(config.zendeskHost() + "/api/v2/tickets.json")
                .addHeader("Authorization", "Basic " + config.authKey())
                .build()
            ).execute();
            if (res.code() != 200) {
                throw new RuntimeException("Request failed");
            }
            final JsonObject root = JsonParser.parseString(new String(res.body().bytes())).getAsJsonObject();
            final List<Ticket> result = new ArrayList<>();
            for (JsonElement ticket : root.getAsJsonArray("tickets")) {
                result.add(new JsonTicket(ticket.getAsJsonObject()));
            }
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
