package com.tradeshift.hackathon.boldmenenvoy.rest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by andreiserea on 9/28/17.
 */
@Component
@Scope("singleton")
public class JerseyRestClient {

    private String server;
    private Client httpClient;
    private Map<String,Response> cachedResults;
    private Boolean useCache;
    private Boolean respond;
    private Integer delay;
    private Integer errorCode;

    @PostConstruct
    public void init() {
        this.server = System.getProperty("UPSTREAM_URL");
        this.httpClient = ClientBuilder.newClient();
        this.useCache = Boolean.valueOf(System.getProperty("useCache", "true"));
        this.cachedResults = new ConcurrentHashMap<>();
        this.respond = true;
        this.delay = 0;
        this.errorCode = 0;
    }

    public Response fwGet(HttpServletRequest request) {
        Invocation.Builder fwrequest = httpClient.target(System.getenv().get("UPSTREAM_URL") + request.getRequestURI()).request();
        //copy headers to fwrequest
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();
            Enumeration<String> headerValues = request.getHeaders(header);
            while(headerValues.hasMoreElements()) {
                String headerValue = headerValues.nextElement();
                System.out.println("Setting header " + header + " to " + headerValue);
                fwrequest.header(header, headerValue);
            }
        }
        Response response = fwrequest.get();
        // cache good responses
        if (this.useCache) {
            if (response.getStatus() < 500) {
                cachedResults.put(request.getRequestURI(), response);
            } else {
                Response cachedResponse = cachedResults.get(request.getRequestURI());
                return cachedResponse != null ? cachedResponse : response;
            }
        }

        return response;
    }

    public Boolean getRespond() {
        return respond;
    }

    public void setRespond(Boolean respond) {
        this.respond = respond;
    }

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}
