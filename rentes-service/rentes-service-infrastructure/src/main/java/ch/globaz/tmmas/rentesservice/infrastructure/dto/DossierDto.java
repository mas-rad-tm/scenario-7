package ch.globaz.tmmas.rentesservice.infrastructure.dto;

import ch.globaz.tmmas.rentesservice.domain.model.Dossier;
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
public class DossierDto extends ResourceSupport {

	private String identifiant;
	private Long requerantId;

	@JsonProperty("id")
	private Long technicalId;

	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate dateEnregistrement;

	private DossierStatus status;


	public DossierDto(){}


	private DossierDto(Long id, String numero, Long requerantId, String dateEnregistrement, DossierStatus status){

		this.identifiant = numero;
		this.requerantId = requerantId;
		this.technicalId = id;
		this.status = status;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

		this.dateEnregistrement = LocalDate.parse(dateEnregistrement,formatter);
	}



	public static DossierDto fromEntity(Dossier dossier){

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		return new DossierDto(dossier.id(),
				dossier.identifiant().identifiant(), dossier.requerantId(),
				dossier.dateEnregistrement().format(formatter), dossier.status());
	}
}
