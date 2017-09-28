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

		String cachedResult=cachedResults.get(request.getRequestURI());
		
		if (cachedResult!=null)
			return cachedResult;
		
		String responseBody = client.get(request.getRequestURI());
		
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
