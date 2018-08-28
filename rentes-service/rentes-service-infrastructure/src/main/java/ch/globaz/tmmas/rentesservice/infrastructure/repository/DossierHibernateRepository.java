package ch.globaz.tmmas.rentesservice.infrastructure.repository;



import ch.globaz.tmmas.rentesservice.domain.event.DossierCreeEvent;
import ch.globaz.tmmas.rentesservice.domain.model.Dossier;
import ch.globaz.tmmas.rentesservice.domain.repository.DossierRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class DossierHibernateRepository extends HibernateRepository implements DossierRepository {



	@Autowired
	ObjectMapper mapper;

	@Autowired
	ApplicationEventPublisher publisher;

	private static final Logger LOGGER = LoggerFactory.getLogger(DossierHibernateRepository.class);

	@Transactional
	@Override
	public Dossier store(Dossier dossier) {

		getSession().saveOrUpdate(dossier);

		publisher.publishEvent(DossierCreeEvent.fromEntity(dossier));

		return dossier;
	}

	@Transactional
	@Override
	public List<Dossier> getAll() {
		return getSession().createQuery("FROM " + Dossier.class.getSimpleName()).list();
	}

	@Transactional
	@Override
	public Optional<Dossier> getById(Long dossierId) {

		LOGGER.debug("{}#getBiyId, dossierId:{}",this.getClass().getName(),dossierId);

		Optional<Dossier> dossier = Optional.ofNullable(getSession().get(Dossier.class,dossierId));

		return dossier;

	}

}
