package com.tradeshift.hackathon.boldmenenvoy.rest;

import javax.ws.rs.core.MultivaluedMap;

/**
 * Created by andreiserea on 9/29/17.
 */
public class BResponse {

    byte[] response;
    MultivaluedMap<String, Object> headers;
    int status;

    public BResponse(byte[] response, MultivaluedMap<String, Object> headers, int status) {
        this.response = response;
        this.headers = headers;
        this.status = status;
    }
}
