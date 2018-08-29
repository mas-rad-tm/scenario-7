package ch.globaz.tmmas.zuulapigateway.application.metrics;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
@Slf4j
@Component
public class RequestCountCollector {

	private Map<String,AtomicInteger> zuulCallByApi = new HashMap<>();
	private Map<String,AtomicInteger> serverCallByApi = new HashMap<>();
	private Map<String,AtomicInteger> serverCallByRoutingHost = new HashMap<>();

	@Autowired
	MeterRegistry registry;


	public void razGauges(){

		zuulCallByApi.values().forEach(counter -> {
				counter.set(0);
		});

		serverCallByApi.values().forEach(counter -> {
			counter.set(0);
		});

		serverCallByRoutingHost.values().forEach(counter -> {
			counter.set(0);
		});
	}


	public void doCollectForRibbonRouting(String path, String ribbonRoutingHost){



		serverCallByRoutingHost.putIfAbsent(ribbonRoutingHost,initialiseGaugeRibbonRouting(path,ribbonRoutingHost));

		serverCallByRoutingHost.computeIfPresent(ribbonRoutingHost,(p,counter)->{
			log.info("Key present :{}, compute..., old value : {}",p,counter.get());
			log.info("new value for key [{}]: {}",p,counter.incrementAndGet());
			return counter;
		});

		log.info("End collecting data :{}", serverCallByRoutingHost);
	}

	public void doCollect(String key, MetricsGauges gauge){

		Map<String,AtomicInteger> mapForGauge;

		if(gauge.equals(MetricsGauges.SERVER_REQUEST_FILTER)){
			mapForGauge = serverCallByApi;
		}else if(gauge.equals(MetricsGauges.ZUUL_REQUEST_FILTER)){
			mapForGauge = zuulCallByApi;
		}else{
			throw new IllegalArgumentException(String.format("The metrics gauge passed is not correct : %s",gauge));
		}

		log.info("Collecting for key count {}, for gauge: {}", key,gauge.gaugeName());

		//si pas présente on initialise
		mapForGauge.putIfAbsent(key, initialiseGaugeRequestPath(key,gauge.gaugeName()));

		mapForGauge.computeIfPresent(key,(path, counter)->{
			log.info("Key present :{}, compute..., old value : {}",path,counter.get());
			log.info("new value for key [{}]: {}",path,counter.incrementAndGet());
			return counter;
		});

		log.info("End collecting data :{}", mapForGauge);
	}


	private AtomicInteger initialiseGaugeRibbonRouting(String path, String host){


		AtomicInteger requestCounter = new AtomicInteger(0);
		Gauge.builder(MetricsGauges.RIBBON_API_ROUTING.gaugeName(), requestCounter, obj-> obj.doubleValue())
				.tag("host", host)
				.tag("api", path)
				.register(registry);

		return requestCounter;

	}


	private AtomicInteger initialiseGaugeRequestPath(String key, String gaugeName){


		AtomicInteger requestCounter = new AtomicInteger(0);
		Gauge.builder(gaugeName, requestCounter, obj-> obj.doubleValue()).tag("api", key).register(registry);

		return requestCounter;

	}


}
