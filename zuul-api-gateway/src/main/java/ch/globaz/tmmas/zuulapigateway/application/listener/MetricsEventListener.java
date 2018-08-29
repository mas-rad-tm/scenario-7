package ch.globaz.tmmas.zuulapigateway.application.listener;

import ch.globaz.tmmas.zuulapigateway.application.event.PrometheusCallEvent;
import ch.globaz.tmmas.zuulapigateway.application.event.ServerFilterEvent;
import ch.globaz.tmmas.zuulapigateway.application.event.ZuulFilterEvent;
import ch.globaz.tmmas.zuulapigateway.application.metrics.MetricsGauges;
import ch.globaz.tmmas.zuulapigateway.application.metrics.RequestCountCollector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MetricsEventListener {

	@Autowired
	RequestCountCollector collector;

	@EventListener
	public void onZuulFilterEvent(ZuulFilterEvent zuulFilterEvent){
		log.info("Zuul filter event: {}",zuulFilterEvent);

		collector.doCollect(zuulFilterEvent.getServicePath(), MetricsGauges.ZUUL_REQUEST_FILTER);
		collector.doCollectForRibbonRouting(zuulFilterEvent.getServicePath(), zuulFilterEvent.getHost());

	}

	@EventListener
	public void onServerFilterEvent(ServerFilterEvent serverFilterEvent){
		log.info("Server filter event: {}",serverFilterEvent);

		collector.doCollect(serverFilterEvent.getKey(), MetricsGauges.SERVER_REQUEST_FILTER);

	}

	@EventListener
	public void onPrometheusCallEvent(PrometheusCallEvent prometheusCallEvent){
		log.info("Prometheus call event: {}",prometheusCallEvent);

		collector.razGauges();

	}
}
