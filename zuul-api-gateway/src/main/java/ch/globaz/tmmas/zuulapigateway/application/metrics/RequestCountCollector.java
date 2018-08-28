package ch.globaz.tmmas.zuulapigateway.application.metrics;

import com.netflix.discovery.AzToRegionMapper;
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

	@Autowired
	MeterRegistry registry;


	public void razGauges(){

		zuulCallByApi.values().forEach(counter -> {
				counter.set(0);
		});

		serverCallByApi.values().forEach(counter -> {
			counter.set(0);
		});
	}



	public void doCollect(String key, String host, MetricsGauges gauge){

		Map<String,AtomicInteger> mapForGauge;

		if(gauge.equals(MetricsGauges.SERVER_REQUEST_FILTER)){
			mapForGauge = serverCallByApi;
		}else{
			mapForGauge = zuulCallByApi;
		}

		log.info("Collecting for key count {}, for gauge: {}", key,gauge.gaugeName());

		//si pas présente on initialise
		mapForGauge.putIfAbsent(key, initialiseGaugeFor(key,host,gauge.gaugeName()));

		mapForGauge.computeIfPresent(key,(path, counter)->{
			log.info("Key present :{}, compute..., old value : {}",path,counter.get());
			log.info("new value for key [{}]: {}",path,counter.incrementAndGet());
			return counter;
		});

		log.info("End collecting data :{}", zuulCallByApi);
	}


	private AtomicInteger initialiseGaugeFor(String key, String host, String gaugeName){


		AtomicInteger requestCounter = new AtomicInteger(0);


			Gauge.builder(gaugeName, requestCounter, obj->
					obj.doubleValue()
			).tag("api", key)
			.tag("host",host)
			.register(registry);



		return requestCounter;

	}


}
