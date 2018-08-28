package ch.globaz.tmmas.rentesservice.application.api.web;

import ch.globaz.tmmas.rentesservice.application.service.DossierService;
import ch.globaz.tmmas.rentesservice.domain.model.Dossier;
import ch.globaz.tmmas.rentesservice.infrastructure.dto.DossierDto;
import ch.globaz.tmmas.rentesservice.infrastructure.dto.DossierEtenduDto;
import ch.globaz.tmmas.rentesservice.infrastructure.dto.PersonnesPhysiqueDto;
import ch.globaz.tmmas.rentesservice.infrastructure.service.FeignPersonnesPhysiqueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.UriTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/dossiers")
public class DossiersController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DossiersController.class);

	private static final String DOSSIERS = "/dossiers";
	private static final String DOSSIER = DOSSIERS + "/{id}";

	@Autowired
	DossierService dossierService;

	@Autowired
	FeignPersonnesPhysiqueService personnePhysiqueService;


	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity createDossier(@RequestBody DossierDto dto){

		LOGGER.debug("createDossier(), {}",dto);

		ResponseEntity<PersonnesPhysiqueDto> personne
                = personnePhysiqueService.getPersonnePhysiqueById(dto.getRequerantId());

		if(personne.getStatusCode().equals(HttpStatus.NOT_FOUND)){

		    LOGGER.debug("createDossier(), no person with this id: {}",dto.getRequerantId());

			return new ResponseEntity<String>("Tiers referenced not exist: " + dto.getRequerantId(), HttpStatus
					.CONFLICT);
		}else{

		    LOGGER.debug("createDossier(), find personne : {}",personne.getBody());

			Dossier dossier = dossierService.sauve(Dossier.builder(
					Long.valueOf(dto.getRequerantId()),
					dto.getDateEnregistrement()));

			DossierDto rdto = DossierDto.fromEntity(dossier);

			rdto.add(linkTo(methodOn(DossiersController.class).getDossierById(rdto.getTechnicalId())).withSelfRel());
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(new UriTemplate(DOSSIER).expand(rdto.getTechnicalId()));


			return new ResponseEntity<>(rdto, HttpStatus.CREATED);
		}

	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<?>> getAllDossier(@RequestParam(value = "etendu", required = false)
			                                            String etendu){
		Boolean dossierEtendu = Boolean.valueOf(etendu);

		List<Dossier> dossiers = dossierService.getAll();


		if(dossierEtendu){
			List<DossierEtenduDto> dossierEtendues = getAllAsDto(dossiers).stream().map(rente -> {

				rente.add(linkTo(methodOn(DossiersController.class)
						.getDossierById(rente.getTechnicalId())).withSelfRel());

				//recup pp dto
				PersonnesPhysiqueDto ppDto =
						personnePhysiqueService.getPersonnePhysiqueById(rente.getRequerantId()).getBody();

				ppDto.add(linkTo(methodOn(FeignPersonnesPhysiqueService.class)
						.getPersonnePhysiqueById(ppDto.getTechnicalId())).withSelfRel());

				return new DossierEtenduDto(rente, ppDto);

			}).collect(Collectors.toList());

			return new ResponseEntity<>(dossierEtendues, HttpStatus.OK);
		}


		return new ResponseEntity<>(getAllAsDto(dossiers), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{personneId}")
	public ResponseEntity getDossierById(@PathVariable Long personneId){

		LOGGER.debug("getDossierById(), {}",personneId);

		Optional<Dossier> dossier = dossierService.getById(personneId);

		if(dossier.isPresent()){
			DossierDto dto = DossierDto.fromEntity(dossier.get());

			dto.add(linkTo(methodOn(DossiersController.class).getDossierById(dto.getTechnicalId())).withSelfRel());
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(new UriTemplate(DOSSIER).expand(dto.getTechnicalId()));


			LOGGER.debug("getPersonById() return  {}",dto);

			return new ResponseEntity<>(dto, HttpStatus.OK);
		}else{

			return new ResponseEntity<>("No entity found with id " + personneId, HttpStatus.NOT_FOUND);
		}

	}

	private List<DossierDto> getAllAsDto (List<Dossier> dossierList) {
		return dossierList.stream().map(DossierDto::fromEntity).collect(Collectors.toList());
	}


}
