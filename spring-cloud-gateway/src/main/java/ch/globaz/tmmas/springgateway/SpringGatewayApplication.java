package ch.globaz.tmmas.springgateway;

import ch.globaz.tmmas.springgateway.filters.PostRequestFilterFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

import static org.springframework.boot.actuate.metrics.web.client.RestTemplateExchangeTags.uri;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;

@SpringBootApplication
@Slf4j
public class SpringGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringGatewayApplication.class);
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()

				.route("personnes", pathFiltre ->
						pathFiltre.path("/personnes/**")
							.filters(filtre ->
								filtre.stripPrefix(1)
							).uri("http://localhost:9010"))

				.route("rentes", pathFiltre ->
						pathFiltre.path("/rentes/**")
								.filters(filtre ->
										filtre.stripPrefix(1)
								)
								.uri("http://localhost:9020"))

				.build();
	}




}
