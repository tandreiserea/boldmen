package com.tradeshift.hackathon.boldmenenvoy.rest;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/**")
public class HttpProxyController {

//	@RequestMapping(method = RequestMethod.GET)
//	public @ResponseBody String getAll(HttpServletRequest request, HttpServletResponse response) {
//		return request.getRequestURI();
//	}
	
//	@RequestMapping("/**")
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody String mirrorRest(HttpMethod method, HttpServletRequest request,
	    HttpServletResponse response) throws URISyntaxException	{
		
		RestTemplate restTemplate = new RestTemplate();
		
	    URI uri = new URI("http", null, "www.google.com", 80, request.getRequestURI(), request.getQueryString(), null);
	    
	    System.out.println(uri.toString());
	    
	    ResponseEntity<String> responseEntity =
	        restTemplate.exchange(uri, method, new HttpEntity<String>(""), String.class);

	    System.out.println(responseEntity.getBody().toString());
	    
	    return responseEntity.getBody();
	}
}
