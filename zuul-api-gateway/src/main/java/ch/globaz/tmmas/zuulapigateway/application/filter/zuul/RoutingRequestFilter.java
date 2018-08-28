package ch.globaz.tmmas.zuulapigateway.application.filter.zuul;

import ch.globaz.tmmas.zuulapigateway.application.event.ZuulFilterEvent;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class RoutingRequestFilter extends ZuulFilter {

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;
	@Autowired
	MeterRegistry registry;

	private Map<String,AtomicInteger> callByAPI = new HashMap<>();

	private final static String PROMETHEUS_API = "/actuator/prometheus";

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Override
	public String filterType() {
		return "route";
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


		RequestContext requestContext = RequestContext.getCurrentContext();
		HttpServletRequest request = requestContext.getRequest();

		applicationEventPublisher.publishEvent(new ZuulFilterEvent(request.getServletPath(),requestContext
				.getRouteHost().toString()));

		logRequestInfo(requestContext, request);

		return null;
	}

	private void logRequestInfo(RequestContext requestContext, HttpServletRequest request) {
		log.info("Request Context Path:{}",request.getContextPath());
		log.info("Resquest Servlet URL:{}",request.getRequestURI());
		log.info("Request Servlet Path:{}",request.getServletPath());
		log.info("Request URI:{}",request.getRequestURI());

		log.info("Route hostI:{}",requestContext.getRouteHost());
		log.info("Route path:{}",requestContext.getRequest().getRequestURL());
		log.info("Route path2:{}",requestContext.getRequest());
		log.info("Path info:{}",requestContext.getRequest().getPathInfo());

		log.info("Response:{}",requestContext.getResponse().getStatus());
	}
}
