package ch.globaz.tmmas.zuulapigateway.application;

import ch.globaz.tmmas.zuulapigateway.application.filter.zuul.PostRequestFilter;
import ch.globaz.tmmas.zuulapigateway.application.filter.zuul.PreRequestFilter;
import ch.globaz.tmmas.zuulapigateway.application.filter.zuul.RoutingRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.env.Environment;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
@ComponentScan(basePackages = "ch.globaz.tmmas")
public class ZuulApiGateway {

	private static final Logger LOGGER = LoggerFactory.getLogger(ZuulApiGateway.class);

	public static void main(String[] args) {

		Environment env= new SpringApplicationBuilder(ZuulApiGateway.class)
				.web(true).run(args).getEnvironment();

		logInitApplicationContext(env);
	}

	@Bean
	public PreRequestFilter preRequestFilter () {
		return new PreRequestFilter();
	}

	@Bean
	public RoutingRequestFilter routingRequestFilter () {
		return new RoutingRequestFilter();
	}

	@Bean
	public PostRequestFilter postRequestFilter () {
		return new PostRequestFilter();
	}
	private static void logInitApplicationContext (Environment env) {

		ResourceProperties prop = new ResourceProperties();
		String[] staticLocation = prop.getStaticLocations();
		LOGGER.info("***********************************************************************");
		LOGGER.info("*                     *** Static location paths ***                   *");
		LOGGER.info("***********************************************************************");

		Arrays.asList(staticLocation).forEach(location -> {
			LOGGER.info("* {}",location);
		});
		LOGGER.info("***********************************************************************");


		String externalAdress;

		try {
			externalAdress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			externalAdress = "Undefined";
		}
		LOGGER.info("***********************************************************************");
		LOGGER.info("*            *** Application context configuration ***                *");
		LOGGER.info("***********************************************************************");

		LOGGER.info("* Application '{}' is running!",env.getProperty("spring.application.name"));
		LOGGER.info("* Local      : localhost:{}{}",
				env.getProperty("server.port"), env.getProperty("server.contextPath"));
		LOGGER.info("* External   : {}:{}{}",externalAdress, env.getProperty("server.port"),
				env.getProperty("server.contextPath"));

		String activeProfiles = Arrays.asList(env.getActiveProfiles()).stream()
				.collect( Collectors.joining( "," ) );
		LOGGER.info("* Profile(s) : {} ",activeProfiles);

		logProxyInfo();

		LOGGER.info("***********************************************************************");

	}

	private static void logProxyInfo() {

		Optional<String> httpProxy = Optional.ofNullable(System.getProperties().getProperty("http.proxyHost"));
		Optional<String> httpsProxy = Optional.ofNullable(System.getProperties().getProperty("https.proxyHost"));

		if(httpProxy.isPresent()){
			LOGGER.info("* HTTP Proxy : {}:{} ",httpProxy,
					System.getProperties().getProperty("http.proxyPort"));
			LOGGER.info("* HTTPS Proxy: {}:{} ",httpsProxy,
					System.getProperties().getProperty("https.proxyPort"));
		}

	}

	@Bean(name = "applicationEventMulticaster")
	public ApplicationEventMulticaster simpleApplicationEventMulticaster() {
		SimpleApplicationEventMulticaster eventMulticaster
				= new SimpleApplicationEventMulticaster();

		eventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
		return eventMulticaster;
	}
}
