package ch.globaz.tmmas.rentesservice.application.service.impl;

import ch.globaz.tmmas.rentesservice.application.service.DossierService;
import ch.globaz.tmmas.rentesservice.domain.repository.DossierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DossierServiceImpl implements DossierService {

	@Autowired
	DossierRepository repository;

	@Override
	public ch.globaz.tmmas.rentesservice.domain.model.Dossier sauve(ch.globaz.tmmas.rentesservice.domain.model.Dossier dossier) {

		return repository.store(dossier);
	}

	@Override
	public List<ch.globaz.tmmas.rentesservice.domain.model.Dossier> getAll() {
		return repository.getAll();
	}

	@Override
	public Optional getById(Long id) {

		Optional<ch.globaz.tmmas.rentesservice.domain.model.Dossier> pp = repository.getById(id);

		return pp;
	}
}
