package com.tradeshift.hackathon.boldmenenvoy.rest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;
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
    private Map<String,BResponse> cachedResults;
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

    public Response fwGet(HttpServletRequest request) throws IOException {
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
                InputStream inputStream = response.readEntity(InputStream.class);
                byte[] bytes = StreamUtils.copyToByteArray(inputStream);
                MultivaluedMap<String, Object> headers = response.getHeaders();
                cachedResults.put(request.getRequestURI(), new BResponse(bytes, response.getHeaders(), response.getStatus()));
                inputStream.close();
            } else if (cachedResults.containsKey(request.getRequestURI())) {
            	System.out.println("returning from cache for url " + request.getRequestURI());

                Response cachedResponse;
                BResponse bresponse = cachedResults.get(request.getRequestURI());
                cachedResponse = Response.status(bresponse.status).entity(bresponse.response).build();

                for (Map.Entry<String, List<Object>> entry : bresponse.headers.entrySet()) {
                    cachedResponse.getHeaders().add(entry.getKey(), entry.getValue());
                }
                return cachedResponse;
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
