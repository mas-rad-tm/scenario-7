package ch.globaz.tmmas.rentesservice.application.service;

import ch.globaz.tmmas.rentesservice.domain.model.Dossier;

import java.util.List;
import java.util.Optional;

public interface DossierService {

	Dossier sauve(Dossier dossier);

	List<Dossier> getAll();

    Optional getById(Long id);
}
