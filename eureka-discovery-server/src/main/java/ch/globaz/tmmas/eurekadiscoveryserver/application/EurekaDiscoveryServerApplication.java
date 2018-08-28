package ch.globaz.tmmas.eurekadiscoveryserver.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@EnableEurekaServer
@EnableDiscoveryClient
@SpringBootApplication
public class EurekaDiscoveryServerApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(EurekaDiscoveryServerApplication.class);


    public static void main(String[] args) {

        Environment env = SpringApplication.run(EurekaDiscoveryServerApplication.class, args).getEnvironment();

        logInitApplicationContext(env);
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
                env.getProperty("server.port"), getContextPath(env));
        LOGGER.info("* External   : {}:{}{}",externalAdress, env.getProperty("server.port"),
                getContextPath(env));

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

    private static String getContextPath(Environment env) {
        return Optional.ofNullable(env.getProperty("server.contextPath")).orElse("/");
    }


}
