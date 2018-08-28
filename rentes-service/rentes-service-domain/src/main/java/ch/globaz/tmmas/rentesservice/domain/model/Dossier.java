package ch.globaz.tmmas.rentesservice.domain.model;

import ch.globaz.tmmas.rentesservice.domain.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@ToString
@EqualsAndHashCode
@Getter
public class Dossier implements Entity<Dossier>{

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private DossierId identifiant;
    private LocalDate dateEnregistrement;
    private Long requerantId;
    private DossierStatus status;


    public DossierId identifiant() {
        return identifiant;
    }

    public Long id() {
        return id;
    }

    public Long requerantId () {
        return requerantId;
    }

    public LocalDate dateEnregistrement() {
        return dateEnregistrement;
    }

    public DossierStatus status () {
        return status;
    }

    public Dossier(LocalDate dateEnregistrement, Long requerantId){
        this.requerantId = requerantId;
        this.dateEnregistrement = dateEnregistrement;
        this.identifiant = DossierId.aleatoire();
        this.status = DossierStatus.INITIE;
    }

    public static Dossier builder(Long reuqerantId, LocalDate dateEnregistrement) {
        return new Dossier(dateEnregistrement,reuqerantId);
    }
    //hibernate
    private Long id;

    Dossier() {}

    public String getDateEnregistrementAsString() {
        return this.dateEnregistrement.format(formatter);
    }

    @Override
    public boolean sameIdentityAs(Dossier dossier) {
        return this.identifiant.equals(dossier.identifiant());
    }
}
