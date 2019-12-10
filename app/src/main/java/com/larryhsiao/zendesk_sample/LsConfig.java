package com.larryhsiao.zendesk_sample;

/**
 * The demo auth credential.
 *
 * TODO: Change these parameters to newer version.
 */
public class LsConfig implements Config {
    @Override
    public String zendeskHost() {
        return "{HOST}";
    }

    @Override
    public String authKey() {
        return "{KEY}";
    }
}
