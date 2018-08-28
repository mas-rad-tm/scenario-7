package ch.globaz.tmmas.personnes.application;

import ch.globaz.tmmas.personnes.application.configuration.DefaultProfileUtil;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;

/**
 * Classe de configuration pour le déploiement en mode war.
 * Remplace la configuration web.xml
 */
@Configuration
public class ApplicationWebInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {

		//DefaultProfileUtil.setProdProfile(application.application());
		DefaultProfileUtil.setDevelopmentProfile(application.application());
		return application.sources(PersonnesServiceApplication.class);
	}
}
