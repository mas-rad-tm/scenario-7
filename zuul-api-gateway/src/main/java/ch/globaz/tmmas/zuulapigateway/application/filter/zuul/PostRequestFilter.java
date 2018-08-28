package ch.globaz.tmmas.zuulapigateway.application.filter.zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class PostRequestFilter extends ZuulFilter {

	@Override
	public String filterType() {
		return "post";
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
		log.info("Request Context Path:{}",request.getContextPath());
		log.info("Resquest Servlet URL:{}",request.getRequestURI());
		log.info("Request Servlet Path:{}",request.getServletPath());
		log.info("Request URI:{}",request.getRequestURI());

		log.info("Route hostI:{}",requestContext.getRouteHost());
		log.info("Route path:{}",requestContext.getRequest());
		log.info("Path info:{}",requestContext.getRequest().getPathInfo());
		log.info("Response:{}",requestContext.getResponse().getStatus());
		try {
			log.info("Response body:{}",requestContext.getResponse().getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
