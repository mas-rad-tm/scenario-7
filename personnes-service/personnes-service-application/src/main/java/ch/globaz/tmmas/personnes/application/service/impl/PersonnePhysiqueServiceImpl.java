package ch.globaz.tmmas.personnes.application.service.impl;

import ch.globaz.tmmas.personnes.application.service.PersonnePhysiqueService;
import ch.globaz.tmmas.personnes.domain.model.PersonnePhysique;
import ch.globaz.tmmas.personnes.domain.repository.PersonnePhysiqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
@Component
public class PersonnePhysiqueServiceImpl implements PersonnePhysiqueService {

    @Autowired
    PersonnePhysiqueRepository repository;

    @Override
    public PersonnePhysique sauve(PersonnePhysique pp) {

        return repository.store(pp);
    }

    @Override
    public List<PersonnePhysique> getAll() {
        return repository.getAll();
    }

    @Override
    public Long countPersonnePhysique() {
        return repository.countAllTiers();
    }

    @Override
    public Optional getById(Long id) {

        Optional<PersonnePhysique> pp = repository.getById(id);

        return pp;
    }
}
