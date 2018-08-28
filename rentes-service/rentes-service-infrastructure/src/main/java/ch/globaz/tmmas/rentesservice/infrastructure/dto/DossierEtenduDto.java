package ch.globaz.tmmas.rentesservice.infrastructure.dto;


import org.springframework.hateoas.ResourceSupport;

public class DossierEtenduDto extends ResourceSupport{

	private DossierDto rente;
	private PersonnesPhysiqueDto requerant;

	public DossierEtenduDto() {}

	public DossierEtenduDto(DossierDto rente, PersonnesPhysiqueDto requerant){
		this.rente = rente;
		this.requerant = requerant;
	}

	public DossierDto getRente () {
		return rente;
	}

	public PersonnesPhysiqueDto getRequerant() {
		return requerant;
	}


}
