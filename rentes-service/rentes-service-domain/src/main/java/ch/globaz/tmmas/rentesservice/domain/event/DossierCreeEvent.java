package ch.globaz.tmmas.rentesservice.domain.event;

import ch.globaz.tmmas.rentesservice.domain.ValueObject;
import ch.globaz.tmmas.rentesservice.domain.model.Dossier;
import ch.globaz.tmmas.rentesservice.domain.model.DossierId;
import ch.globaz.tmmas.rentesservice.domain.model.DossierStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@EqualsAndHashCode
@ToString
@Getter
public class DossierCreeEvent implements ValueObject<DossierCreeEvent> {

    private String identifiant;
    private String dateEnregistrement;
    private Long requerantId;
    private String status;
    private Long id;

    public DossierCreeEvent(Long id,String identifiant, String dateEnregistrement, Long requerantId, String status) {
        this.identifiant = identifiant;
        this.dateEnregistrement = dateEnregistrement;
        this.requerantId = requerantId;
        this.status = status;
        this.id = id;
    }

    public DossierCreeEvent(){}


    @Override
    public boolean sameValueAs(DossierCreeEvent other) {
        return this.equals(other);

    }
    public static DossierCreeEvent fromEntity(Dossier dossier) {
        return new DossierCreeEvent(dossier.getId(),
                dossier.getIdentifiant().identifiant(),dossier
                .getDateEnregistrementAsString(),
                dossier
                .requerantId(),
                dossier.status().toString());
    }

}
