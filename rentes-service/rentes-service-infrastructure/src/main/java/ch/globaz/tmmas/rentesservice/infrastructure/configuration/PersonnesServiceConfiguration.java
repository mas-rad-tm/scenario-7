package ch.globaz.tmmas.rentesservice.infrastructure.configuration;


import ch.globaz.tmmas.rentesservice.infrastructure.configuration.feign.NotFoundDecoder;
import feign.codec.Decoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.support.ResponseEntityDecoder;
import org.springframework.cloud.netflix.feign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
public class PersonnesServiceConfiguration {

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {

		return new RestTemplate();
	}

	@Bean
	public Decoder notFoundDecoder(ObjectFactory<HttpMessageConverters> messageConverters) {
		return new NotFoundDecoder(new ResponseEntityDecoder(new SpringDecoder(messageConverters)));
	}


}
