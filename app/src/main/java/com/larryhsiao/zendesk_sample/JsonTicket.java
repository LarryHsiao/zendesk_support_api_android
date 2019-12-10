package com.larryhsiao.zendesk_sample;

import com.google.gson.JsonObject;

/**
 * Json implementation of a ticket returned by Zendesk api.
 */
public class JsonTicket implements Ticket {
    private final JsonObject object;

    public JsonTicket(JsonObject object) {
        this.object = object;
    }

    @Override
    public String title() {
        return object.get("subject").getAsString();
    }

    @Override
    public String content() {
        return object.get("description").getAsString();
    }
}
