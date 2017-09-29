package com.tradeshift.hackathon.boldmenenvoy.rest.controller;

import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.tradeshift.hackathon.boldmenenvoy.rest.JerseyRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Path("/{any : .*}")
public class HttpProxyController {

	static Logger LOGGER = LoggerFactory.getLogger(HttpProxyController.class);

	@Autowired
    private JerseyRestClient client;

    @Context HttpServletRequest request;

	@GET
	public Response mirrorRest(@Context HttpServletResponse httpServletResponse) throws URISyntaxException {

	    System.out.println("Received request to: " + request.getRequestURI());
        Response.ResponseBuilder responseBuilder;
		/*
		String cachedResult=cachedResults.get(request.getRequestURI());
		
		if (cachedResult!=null)
			return cachedResult;
		*/
		/*
		respond: true/false
		delay: integer (seconds)
		errorCode: http_error_code
		*/
		
		if (!client.getRespond()) {
            return Response.status(503).build();
        }

        try
        {
            int nSleep=client.getDelay()*1000;
            Thread.sleep(nSleep);
        }
        catch (Exception e) {}

//		if (errorCode!=null)
//		{
//			try
//			{
//				int nCode=(new Integer(errorCode)).intValue();
//				responseBuilder = Response.status(nCode);
////				response.setStatus(nCode);
//			}
//			catch (Exception e) {
//
//			}
//
//		}
        return client.fwGet(this.request);
	}
}
