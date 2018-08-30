package ch.globaz.tmmas.springgateway.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.setResponseStatus;

@Slf4j
public class PostRequestFilterFactory extends AbstractGatewayFilterFactory<PostRequestFilterFactory.Config> {


	@Override
	public GatewayFilter apply(Config config) {
		final HttpStatus status = ServerWebExchangeUtils.parse(config.status);
		return (exchange, chain) -> {

			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				log.info("After");
				// check not really needed, since it is guarded in setStatusCode,
				// but it's a good example
				if (!exchange.getResponse().isCommitted()) {
					setResponseStatus(exchange, status);
				}
			}));
		};
	}

	public static final String STATUS_KEY = "status";

	public PostRequestFilterFactory() {
		super(Config.class);
	}

	@Override
	public List<String> shortcutFieldOrder() {
		return Arrays.asList(STATUS_KEY);
	}

	public static class Config {
		//TODO: relaxed HttpStatus converter
		private String status;

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}
	}
}
