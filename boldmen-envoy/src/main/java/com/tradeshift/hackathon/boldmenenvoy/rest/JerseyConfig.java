package com.tradeshift.hackathon.boldmenenvoy.rest;

import com.tradeshift.hackathon.boldmenenvoy.rest.controller.HttpProxyController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

/**
 * Created by andreiserea on 9/28/17.
 */
@Configuration
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        // scan the resources package for our resources
        register(HttpProxyController.class);
        register(SettingsService.class);
    }
}
