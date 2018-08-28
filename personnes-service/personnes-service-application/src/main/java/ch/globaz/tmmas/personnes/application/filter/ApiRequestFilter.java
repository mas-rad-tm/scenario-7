package ch.globaz.tmmas.personnes.application.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
@WebFilter("/*")
public class ApiRequestFilter implements Filter{

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiRequestFilter.class);


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOGGER.info("do");
        MDC.put("reqId",UUID.randomUUID().toString());
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
        LOGGER.info("destroy");
    }
}
