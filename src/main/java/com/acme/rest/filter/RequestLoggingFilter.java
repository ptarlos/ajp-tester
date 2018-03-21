package com.acme.rest.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.filter.OncePerRequestFilter;

public class RequestLoggingFilter extends OncePerRequestFilter {

	protected static final Log log = LogFactory.getLog("REQUEST_LOG");
	protected static final Log perfLog = LogFactory.getLog("PERFORMANCE_LOG");

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			final FilterChain filterChain) throws ServletException, IOException {
		long beginTime = System.currentTimeMillis();
		logRequest(request);

		filterChain.doFilter(request, response);

		logResponse(response);
		logPerformance(request, response, beginTime);
	}

	protected void logRequest(HttpServletRequest req) {

		if (log.isDebugEnabled()) {
			log.debug("*** HttpServletRequest ***");
			String queryString = (req.getQueryString() == null) ? "" : ("?" + req.getQueryString());
			log.debug(req.getMethod() + " " + req.getRequestURI() + queryString);
			Enumeration<String> headerNames = req.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String headerName = headerNames.nextElement();
				log.debug(String.format("Header: [%s=%s]", headerName, req.getHeader(headerName)));
			}
		}
	}

	protected void logResponse(HttpServletResponse resp) {
		if (log.isDebugEnabled()) {
			log.debug("*** HttpServletResponse ***");
			log.debug("Status: " + resp.getStatus());
			Collection<String> headerNames = resp.getHeaderNames();
			for (String headerName : headerNames) {
				log.debug(String.format("Header: [%s=%s]", headerName, resp.getHeader(headerName)));
			}
		}
	}

	protected void logPerformance(HttpServletRequest req, HttpServletResponse resp, long beginTime) {
		if (perfLog.isDebugEnabled()) {
			long procTime = System.currentTimeMillis() - beginTime;
			String ipAddress = req.getHeader("X-FORWARDED-FOR");
			if (ipAddress == null) {
				ipAddress = req.getRemoteAddr();
			}
			perfLog.debug(String.format("%s '%s %s %s' %s (%s ms)", ipAddress, req.getMethod(), req.getRequestURI(),
					req.getProtocol(), resp.getStatus(), procTime));
		}
	}
}
