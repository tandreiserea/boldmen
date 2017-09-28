package com.tradeshift.hackathon.boldmenenvoy.rest.controller;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tradeshift.hackathon.boldmenenvoy.controllers.DelegateController;
import com.tradeshift.hackathon.boldmenenvoy.rest.RestClient;

@RestController
public class HttpProxyController {

	static Logger LOGGER = LoggerFactory.getLogger(HttpProxyController.class);

	@Autowired
	RestClient client;

	static Map<String,String> cachedResults = new HashMap<String,String>();
	static Map<String,String> settings = new HashMap<String,String>();
	
	@RequestMapping(value="/**",method = RequestMethod.GET)
	public @ResponseBody String mirrorRest(HttpMethod method, HttpServletRequest request,
			HttpServletResponse response) throws URISyntaxException {

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
		
		String useCache=System.getenv("useCache");

		if (useCache==null)
			useCache="false";
		
		String respondValue=settings.get("respond");
		String delayValue=settings.get("delay");
		String errorCode=settings.get("errorCode");
		
		if (respondValue!=null)
			if (respondValue.equals("false"))
			{
				try
				{
					Thread.sleep(1000000000);
				}
				catch (Exception e) 
				{
					
				}
				return "";
			}
		
		if (delayValue!=null)
		{
			try
			{
				int nSleep=(new Integer(delayValue)).intValue()*1000;
				Thread.sleep(nSleep);
			}
			catch (Exception e) {
				
			}
		}
		
		if (errorCode!=null)
		{
			try
			{
				int nCode=(new Integer(errorCode)).intValue();
				response.setStatus(nCode);
			}
			catch (Exception e) {
				
			}
			
		}
		ResponseEntity<String> proxyResponse=client.get(request.getRequestURI());
		String responseBody = proxyResponse.getBody();
		
		HttpStatus statusCode = proxyResponse.getStatusCode();
		
		if (statusCode.is5xxServerError())
		{
			if (useCache.equals("true"))
			{
				String cachedResult=cachedResults.get(request.getRequestURI());
			
				if (cachedResult!=null)
					return cachedResult;
				else
				{
					response.setStatus(504);
					return "Gateway Timeout";
				}
			}
		}
		
		if (useCache.equals("true"))
			cachedResults.put(request.getRequestURI(), responseBody);
		
		return responseBody;
	}
	
	@RequestMapping(value = "/delegate", method = RequestMethod.GET)
    public String delegate(@RequestParam(value = "dest", required = true) String dest) throws ParseException {
        LOGGER.info("delegate to others", DelegateController.class);
    	return "redirect:" + "http://www.google.com";
    }
	
	@RequestMapping(value = "/settings", method = RequestMethod.PUT)
	public void delegate(@RequestParam(value = "key", required = true) String key,
			@RequestParam(value = "value", required = true) String value)
			throws ParseException, InterruptedException {

		settings.put(key, value);
	}
	
}
