package ch.globaz.tmmas.personnes.application.api.web;

import ch.globaz.tmmas.personnes.application.api.dto.PersonnesPhysiqueDto;
import ch.globaz.tmmas.personnes.application.service.PersonnePhysiqueService;
import ch.globaz.tmmas.personnes.domain.model.NSS;
import ch.globaz.tmmas.personnes.domain.model.PersonnePhysique;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.UriTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


@RestController
@RequestMapping("/personnes")
public class PersonnesController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PersonnesController.class);

	private static final String PERSONNES = "/personnes";
	private static final String PERSONNE = PERSONNES + "/{id}";


	@Autowired
	PersonnePhysiqueService personneService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<PersonnesPhysiqueDto> createPersonne(@RequestBody PersonnesPhysiqueDto dto){
		LOGGER.debug("createPerson(), {}",dto);

		PersonnePhysique pp = personneService.sauve(PersonnePhysique.builder(dto.getNom(),dto.getPrenom(),dto
				.getDateNaissance(),new NSS(dto.getNss())));

		LOGGER.debug("createPerson() return  {}",pp);

		PersonnesPhysiqueDto dtop = PersonnesPhysiqueDto.fromEntity(pp);

		dtop.add(linkTo(methodOn(PersonnesController.class).getPersonneById(pp.id())).withSelfRel());
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(new UriTemplate(PERSONNE).expand(pp.id()));


		return new ResponseEntity<>(dtop, headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{personneId}")
	public ResponseEntity getPersonneById(@PathVariable Long personneId){

		LOGGER.debug("getPersonById(), {}",personneId);

		Optional<PersonnePhysique> pp = personneService.getById(personneId);

		if(pp.isPresent()){
			PersonnesPhysiqueDto dto = PersonnesPhysiqueDto.fromEntity(pp.get());

			dto.add(linkTo(methodOn(PersonnesController.class).getPersonneById(pp.get().id())).withSelfRel());
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(new UriTemplate(PERSONNE).expand(pp.get().id()));


			LOGGER.debug("getPersonById() return  {}",dto);

			return new ResponseEntity<>(dto, HttpStatus.OK);
		}else{

			return new ResponseEntity<>("No entity found with id " + personneId, HttpStatus.NOT_FOUND);
		}

	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<PersonnesPhysiqueDto>> getAllPersonnes(@RequestParam(value = "countOnly", required =
			false)
			                                                               String countOnlyValue){

		Boolean countOnly = Boolean.valueOf(countOnlyValue);
		List<PersonnePhysique> personnesPhysique;
		HttpHeaders headers = new HttpHeaders();
		Long numberOfEntries = personneService.countPersonnePhysique();

		headers.add("X-Total-Count", String.valueOf(numberOfEntries));

		if(countOnly){
			personnesPhysique = new ArrayList<>();

		}else{
			personnesPhysique = personneService.getAll();
		}



		return new ResponseEntity<>(getAllAsDto(personnesPhysique),headers, HttpStatus.OK);
	}

	private List<PersonnesPhysiqueDto> getAllAsDto (List<PersonnePhysique> ppList) {
		return ppList.stream().map(personne -> {
			PersonnesPhysiqueDto dto = PersonnesPhysiqueDto.fromEntity(personne);
			dto.add(linkTo(methodOn(PersonnesController.class).getPersonneById(personne.id())).withSelfRel());
			return dto;
		}).collect(Collectors.toList());
	}


}
