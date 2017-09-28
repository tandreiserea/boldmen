package com.tradeshift.hackathon.boldmenenvoy.rest.controller;

import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tradeshift.hackathon.boldmenenvoy.rest.RestClient;

@RestController
@RequestMapping("/**")
public class HttpProxyController {

	@Autowired
	RestClient client;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody String mirrorRest(HttpMethod method, HttpServletRequest request,
			HttpServletResponse response) throws URISyntaxException {

		String responseBody = client.get(request.getRequestURI());
		return responseBody;
	}
}
