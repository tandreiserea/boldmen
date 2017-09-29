package com.tradeshift.hackathon.boldmenenvoy.rest;

import java.util.Map.Entry;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

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
            case "envoy":
                settings.setEnvoy(Boolean.valueOf(settingValue));
                return true;
            case "useCache":
                settings.setUseCache(Boolean.valueOf(settingValue));
                return true;
                
        }
        return false;
    }
    
    @GET
    @Path("/{settingName}")
    @Consumes(MediaType.TEXT_PLAIN)
    public String getSetting(@PathParam("settingName") String settingName) {
        switch(settingName) {
            case "respond":
                return "respond = " + settings.getRespond();
            case "delay":
            	return "delay = " + settings.getDelay();
            case "errorCode":
            	return "errorCode = " + settings.getErrorCode();
            case "envoy":
            	return "envoy = " + settings.getEnvoy();
            case "upstreamServer":
            	return "upstreamServer = " + settings.getServer();
            case "envoyURL":
            	return "envoyURL = " + settings.getEnvoyURL();
            case "useCache":
            	return "useCache = " + settings.getUseCache();
            case "cachedResults":
            	return "cachedResults = " + settings.getCachedResults().toString();
            
        }
        return "not found";
    }
    
    @GET
    @Path("/all")
    @Consumes(MediaType.TEXT_PLAIN)
    public String getAllSettings() {
    	StringBuilder b = new StringBuilder("All settings:");
    	b.append("\n");
    	b.append("respond = ").append(settings.getRespond()).append("\n");
    	b.append("delay = ").append(settings.getDelay()).append("\n");
    	b.append("errorCode = ").append(settings.getErrorCode()).append("\n");
    	b.append("envoy = ").append(settings.getEnvoy()).append("\n");
    	b.append("upstreamServer = ").append(settings.getServer()).append("\n");
    	b.append("envoyURL = ").append(settings.getEnvoyURL()).append("\n");
    	b.append("useCache = ").append(settings.getUseCache()).append("\n");
    	b.append("cachedResults = ").append("\n").append("[");
    	for(Entry<String,BResponse> entry: settings.getCachedResults().entrySet())
    	{
    		b.append(entry.getKey()).append("= ").append(entry.getValue().toString()).append("\n");
    	}
    	b.append("]");
    	return b.toString();
    }
}
