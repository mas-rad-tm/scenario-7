package ch.globaz.tmmas.personnes.application.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class to load a Spring profile to be used as default
 * when there is no <code>spring.profiles.active</code> set in the environment or as command line argument.
 * If the value is not available in <code>application-dev.yml</code> then <code>dev</code> profile will be used as default.
 */
public final class DefaultProfileUtil {

    private static final String SPRING_PROFILE_ACTIVE = "spring.profiles.active";

    private DefaultProfileUtil() {
    }

    public static void setProdProfile (SpringApplication app) {
        Map<String, Object> defProperties =  new HashMap<>();
        defProperties.put(SPRING_PROFILE_ACTIVE, Profiles.PRODUCTION.value());
        app.setDefaultProperties(defProperties);
    }
    /**
     * Set a default to use when no profile is configured.
     *
     * @param app the Spring application
     */
    public static void setDevelopmentProfile(SpringApplication app) {
        Map<String, Object> defProperties =  new HashMap<>();
        /*
        * The default profile to use when no other profiles are defined
        * This cannot be set in the <code>application-dev.yml</code> file.
        * See https://github.com/spring-projects/spring-boot/issues/1219
        */
        defProperties.put(SPRING_PROFILE_ACTIVE, Profiles.DEV.value());
        //defProperties.put(SPRING_PROFILE_DEFAULT, Avs4PocConstants.SPRING_PROFILE_PRODUCTION);
        app.setDefaultProperties(defProperties);
    }


    /**
     * Get the profiles that are applied else get default profiles.
     */
    public static String[] getActiveProfiles(Environment env) {
        String[] profiles = env.getActiveProfiles();
        if (profiles.length == 0) {
            return env.getDefaultProfiles();
        }
        return profiles;
    }

}
