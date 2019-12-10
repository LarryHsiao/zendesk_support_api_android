package com.larryhsiao.zendesk_sample;

/**
 * Config for Zendesk.
 */
public interface Config {
    /**
     * Host of zendesk.
     */
    String zendeskHost();

    /**
     * The key for accessing Zendesk api..
     */
    String authKey();
}
