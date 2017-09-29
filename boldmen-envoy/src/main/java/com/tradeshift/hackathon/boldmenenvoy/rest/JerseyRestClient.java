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
    private Boolean envoy;
    private String envoyURL;

    @PostConstruct
    public void init() {
        this.server = System.getProperty("UPSTREAM_URL");
        this.envoyURL = System.getProperty("UPSTREAM_URL_ENVOY");
        this.httpClient = ClientBuilder.newClient();
        this.useCache = Boolean.valueOf(System.getProperty("useCache", "true"));
        this.cachedResults = new ConcurrentHashMap<>();
        this.respond = true;
        this.envoy = Boolean.valueOf(System.getProperty("envoy", "false"));
        this.delay = 0;
        this.errorCode = 0;
    }

    public Response fwGet(HttpServletRequest request) {
        Invocation.Builder fwrequest = httpClient.target((this.envoy ? this.envoyURL : this.server) + request.getRequestURI()).request();
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
        	System.out.println("response status: " + response.getStatus());
            if (response.getStatus() < 500) {
                cachedResults.put(request.getRequestURI(), response);
            } else {
            	System.out.println("returning from cache for url " + request.getRequestURI());
                Response cachedResponse = cachedResults.get(request.getRequestURI());
                return cachedResponse != null ? cachedResponse : response;
            }
        }

        return response;
    }

    public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public Boolean getUseCache() {
		return useCache;
	}

	public void setUseCache(Boolean useCache) {
		this.useCache = useCache;
	}

	public String getEnvoyURL() {
		return envoyURL;
	}

	public void setEnvoyURL(String envoyURL) {
		this.envoyURL = envoyURL;
	}

	public Map<String, Response> getCachedResults() {
		return cachedResults;
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

    public Boolean getEnvoy() {
        return envoy;
    }

    public void setEnvoy(Boolean envoy) {
        this.envoy = envoy;
    }
}
