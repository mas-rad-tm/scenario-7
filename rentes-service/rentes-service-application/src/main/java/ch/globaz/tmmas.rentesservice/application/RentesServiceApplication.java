package ch.globaz.tmmas.rentesservice.application;

import ch.globaz.tmmas.rentesservice.application.configuration.Profiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Classe principale de l'application en mode jar.
 * Le serveur est embarqué par l'application
 * <b>Enable AutoConfiguration Exclusion:</b>HibernateJpaAutoConfiguration.class
 *
 */

@SpringBootApplication
@EnableAutoConfiguration(exclude = HibernateJpaAutoConfiguration.class)
@EnableDiscoveryClient
@EnableFeignClients(basePackages="ch.globaz.tmmas.rentesservice.infrastructure.service")
@ComponentScan(basePackages = {"ch.globaz.tmmas.rentesservice"})
public class RentesServiceApplication {



	private final Environment env;
	private static final Logger LOGGER = LoggerFactory.getLogger(RentesServiceApplication.class);

	public RentesServiceApplication(Environment env) {
		this.env = env;
	}

	/**
	 * Méthode exécutable démarrant l'application en mode jar
	 * @param args les arguments d'entrées
	 */
	public static void main(String []args)  {

		SpringApplication app = new SpringApplication(RentesServiceApplication.class);
		//DefaultProfileUtil.setDevelopmentProfile(app);

		Environment env = app.run(args).getEnvironment();

		logInitApplicationContext(env);

	}



	@PostConstruct
	public void initApplication() {

		checkProfilesIntegrity();

	}

	private void checkProfilesIntegrity () {
		Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
		if (activeProfiles.contains(Profiles.DEV.value()) && activeProfiles.contains(
				Profiles.PRODUCTION.value())) {
			LOGGER.error("You have misconfigured your application! It should not run " +
					"with both the 'dev' and 'prod' profiles at the same time.");
		}
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
}
