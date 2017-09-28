package com.tradeshift.hackathon.boldmenenvoy.controllers;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;

//@Controller

public class DelegateController {

	static Logger LOGGER = LoggerFactory.getLogger(DelegateController.class);

	/*
	
//	@RequestMapping(value = "/**")
//	@ResponseBody
    public String homePage(HttpServletRequest req, HttpServletResponse res) throws ParseException {
        LOGGER.info("URI: " + req.getRequestURI());
    	return "dudu";
    }

//	@RequestMapping(value = "/delegate", method = RequestMethod.GET)
    public String delegate(@RequestParam(value = "dest", required = true) String dest) throws ParseException {
        LOGGER.info("delegate to others", DelegateController.class);
    	return "redirect:" + "http://www.google.com";
    }

	*/
}
