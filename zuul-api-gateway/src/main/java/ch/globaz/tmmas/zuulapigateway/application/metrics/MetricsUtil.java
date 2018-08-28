package ch.globaz.tmmas.zuulapigateway.application.metrics;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;

import java.util.concurrent.atomic.AtomicInteger;

public class MetricsUtil {


	public static AtomicInteger initialiseGaugeFor(String key, String gaugeName, MeterRegistry registry){

		AtomicInteger requestCounter = new AtomicInteger(0);

		Gauge gauge = Gauge.builder(gaugeName, requestCounter, (obj)->{
			return obj.doubleValue();
		})
				.tags("api", key)
				.register(registry);

		return requestCounter;

	}
}
