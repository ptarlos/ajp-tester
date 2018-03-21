package com.acme.rest.api.controller;

import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.acme.rest.api.service.ValidationHelper;
import com.github.jrialland.ajpclient.Forward;
import com.github.jrialland.ajpclient.SimpleForwardRequest;
import com.github.jrialland.ajpclient.SimpleForwardResponse;
import com.github.jrialland.ajpclient.impl.CPingImpl;
import com.github.jrialland.ajpclient.pool.Channels;

import io.netty.channel.Channel;

@RestController
public class AJPController {

	private Log log = LogFactory.getLog(this.getClass());

	@RequestMapping("/ajpCPing")
	public ResponseEntity<?> ajpCPing(@RequestParam(value = "ip") String ip, @RequestParam(value = "port") int port) {
		if (log.isDebugEnabled()) {
			log.debug(String.format("AJP CPing Request ip: [%s] port: [%s]", ip, port));
		}
		ValidationHelper.assertRequiredParam(ip, "ip");
		ValidationHelper.assertRequiredParam(port, "port");
		boolean pong = false;
		try {
			pong = new CPingImpl(3, TimeUnit.SECONDS).execute(ip, port);
		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage(), ex);
		}

		return ResponseEntity.ok(pong ? "SUCCESS" : "FAIL");
	}

	@RequestMapping("/ajpGet")
	public ResponseEntity<?> ajpGet(@RequestParam(value = "ip") String ip, @RequestParam(value = "port") int port,
			@RequestParam(value = "query") String query) {
		if (log.isDebugEnabled()) {
			log.debug(String.format("AJP GET Request ip: [%s] port: [%s] query: [%s]", ip, port, query));
		}
		ValidationHelper.assertRequiredParam(ip, "ip");
		ValidationHelper.assertRequiredParam(port, "port");
		ValidationHelper.assertRequiredParam(query, "query");

		SimpleForwardRequest request = new SimpleForwardRequest();
		SimpleForwardResponse response = new SimpleForwardResponse();
		request.setRequestUri(query);

		final Channel channel = Channels.connect(ip, port);
		try {
			new Forward(request, response).execute(channel);
		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage(), ex);
		}
		String resp = response.getResponseBodyAsString();
		int statusCode = response.getStatusCode();

		return ResponseEntity.status(statusCode).body(resp);
	}

	@RequestMapping("/status")
	public ResponseEntity<?> status() {
		log.debug("status invoked");
		return ResponseEntity.ok().body("server is up :)");
	}

}
