package ch.globaz.tmmas.zuulapigateway.application.filter.zuul;

import ch.globaz.tmmas.zuulapigateway.application.event.ZuulFilterEvent;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.FilterType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class PreRequestFilter extends ZuulFilter {



	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {

		log.info("####### Running request pre filter...}");

		RequestContext requestContext = RequestContext.getCurrentContext();
		HttpServletRequest request = requestContext.getRequest();

		logRequestInfo(requestContext, request);


		log.info("####### Pre request filter runned}");

		return null;
	}


	private void logRequestInfo(RequestContext requestContext, HttpServletRequest request) {
		log.debug("Request Context Path:{}",request.getContextPath());
		log.debug("Resquest Servlet URL:{}",request.getRequestURI());
		log.debug("Request Servlet Path:{}",request.getServletPath());
		log.debug("Path info:{}",request.getPathInfo());
		log.debug("Request URI:{}",request.getRequestURI());

		log.debug("Route host:{}",requestContext.getRouteHost());
	//	log.debug("Path host:{}",requestContext.getRouteHost().getPath());
		log.debug("Response status:{}",requestContext.getResponse().getStatus());
	}



}
