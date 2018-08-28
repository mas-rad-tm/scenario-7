package ch.globaz.tmmas.rentesservice.infrastructure.dto;


import ch.globaz.tmmas.rentesservice.domain.model.DossierStatus;

import ch.globaz.tmmas.rentesservice.infrastructure.dto.localdate.LocalDateDeserializer;
import ch.globaz.tmmas.rentesservice.infrastructure.dto.localdate.LocalDateSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.ToString;
import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@ToString
@Getter
public class PersonnesPhysiqueDto extends ResourceSupport{

	private String nom;
	private String prenom;

	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate dateNaissance;

	private String nss;

	private DossierStatus status;


	@JsonProperty("id")
	private Long technicalId;


	public PersonnesPhysiqueDto(){}


	private PersonnesPhysiqueDto(Long id,String nom, String prenom, String dateNaissance, String nss){

		this.nom = nom;
		this.prenom = prenom;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

		this.dateNaissance = LocalDate.parse(dateNaissance,formatter);
		this.nss = nss;
		this.technicalId = id;
	}




}
