package ch.globaz.tmmas.rentesservice.domain.repository;

import ch.globaz.tmmas.rentesservice.domain.model.Dossier;

import java.util.List;
import java.util.Optional;

public interface DossierRepository {

	ch.globaz.tmmas.rentesservice.domain.model.Dossier store (ch.globaz.tmmas.rentesservice.domain.model.Dossier dossier);


	List<ch.globaz.tmmas.rentesservice.domain.model.Dossier> getAll();


    Optional<ch.globaz.tmmas.rentesservice.domain.model.Dossier> getById(Long dossierId);
}
