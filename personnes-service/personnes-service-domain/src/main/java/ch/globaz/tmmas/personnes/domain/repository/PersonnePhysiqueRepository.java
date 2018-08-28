package ch.globaz.tmmas.personnes.domain.repository;

import ch.globaz.tmmas.personnes.domain.model.PersonnePhysique;

import java.util.List;
import java.util.Optional;

public interface PersonnePhysiqueRepository {

	PersonnePhysique store(PersonnePhysique pp);

	List<PersonnePhysique> getAll();

	Long countAllTiers();

	Optional<PersonnePhysique> getById(Long personneId);
}
