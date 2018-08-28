package ch.globaz.tmmas.personnes.infrastructure.repository;

import ch.globaz.tmmas.personnes.domain.event.PersonnesPhysiqueCreeEvent;
import ch.globaz.tmmas.personnes.domain.model.PersonnePhysique;
import ch.globaz.tmmas.personnes.domain.repository.PersonnePhysiqueRepository;
import ch.globaz.tmmas.personnes.infrastructure.repository.shared.HibernateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Component
public class PersonnePhysiqueHibernateRepository extends HibernateRepository implements PersonnePhysiqueRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(PersonnePhysiqueHibernateRepository.class);

	@Autowired
	ApplicationEventPublisher publisher;

	@Transactional
	@Override
	public PersonnePhysique store(PersonnePhysique pp) {

		getSession().save(pp);

		publisher.publishEvent(PersonnesPhysiqueCreeEvent.fromEntity(pp));

		return pp;
	}

	@Transactional
	@Override
	public List<PersonnePhysique> getAll() {
		return getSession().createQuery("FROM " + PersonnePhysique.class.getSimpleName()).list();

	}

	@Transactional
	@Override
	public Long countAllTiers() {

		Long count = (Long)getSession().createQuery("SELECT COUNT(1) FROM  " + PersonnePhysique.class.getSimpleName())
				.getSingleResult();

		return count;

	}

	@Transactional
	@Override
	public Optional<PersonnePhysique> getById(Long personneId) {

		LOGGER.debug("{}#getBiyId, personneId:{}",this.getClass().getName(),personneId);

		Optional<PersonnePhysique> pp = Optional.ofNullable(getSession().get(PersonnePhysique.class,personneId));

		//LOGGER.debug("pp: {}:",pp.get());

		return pp;

	}



}
