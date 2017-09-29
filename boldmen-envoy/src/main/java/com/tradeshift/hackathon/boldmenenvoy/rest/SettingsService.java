package com.tradeshift.hackathon.boldmenenvoy.rest;

import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by andreiserea on 9/13/17.
 */
@Path("/boldmen_settings")
public class SettingsService {

    @Autowired
    private JerseyRestClient settings;

    @POST
    @Path("/{settingName}")
    @Consumes(MediaType.TEXT_PLAIN)
    public boolean setSetting(String settingValue, @PathParam("settingName") String settingName) {
        switch(settingName) {
            case "respond":
                settings.setRespond(Boolean.valueOf(settingValue));
                return true;
            case "delay":
                settings.setDelay(Integer.valueOf(settingValue));
                return true;
            case "errorCode":
                settings.setErrorCode(Integer.valueOf(settingValue));
                return true;
        }
        return false;
    }
}
