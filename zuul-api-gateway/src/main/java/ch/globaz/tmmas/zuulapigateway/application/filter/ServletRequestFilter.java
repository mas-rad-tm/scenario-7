package ch.globaz.tmmas.zuulapigateway.application.filter;

import ch.globaz.tmmas.zuulapigateway.application.event.PrometheusCallEvent;
import ch.globaz.tmmas.zuulapigateway.application.event.ServerFilterEvent;
import com.netflix.zuul.context.RequestContext;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 * Filtre consistant à fournir un metrique comptant les appels effectués
 * Sur chaque API.
 * </p>
 * <p>Chaque fois que l'api <b>prometheus</b> est appelé
 * le compteur est remis à zéro. La finalité est de fournir un comtage des appels par untié de temps.
 * </p>
 */
@Slf4j
@Component
public class ServletRequestFilter implements Filter {

    private Map<String,AtomicInteger> countersByApi = new HashMap<>();

    private final static String PROMETHEUS_API = "/actuator/prometheus";

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    MeterRegistry registry;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.debug("Initializing filter :{}", this);
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        RequestContext requestContext = RequestContext.getCurrentContext();

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = ((HttpServletRequest) request).getServletPath();
        log.info("Filter with path : {}", path);

        applicationEventPublisher.publishEvent(new ServerFilterEvent(path,request.getRemoteHost()));

        chain.doFilter(request, response);

        if (path.equals(PROMETHEUS_API)) {
           applicationEventPublisher.publishEvent(new PrometheusCallEvent());
        }

        log.info("Logging Response :{}", res.getContentType());

    }

    @Override
    public void destroy() {
        log.warn("Destructing filter :{}", this);
    }


}
