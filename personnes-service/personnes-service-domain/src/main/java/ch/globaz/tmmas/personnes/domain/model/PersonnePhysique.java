package ch.globaz.tmmas.personnes.domain.model;

import ch.globaz.tmmas.personnes.domain.Entity;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
@ToString
@Getter
public class PersonnePhysique implements Entity<PersonnePhysique>{

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private NSS nss;
    private PersonnePhysiqueId identifiant;

    private PersonnePhysique(String nom, String prenom, LocalDate dateNaissance, NSS nss) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.nss = nss;
        this.identifiant = PersonnePhysiqueId.aleatoire();
    }

   public PersonnePhysique(Long id,String nom, String prenom, LocalDate dateNaissance, NSS nss) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.nss = nss;
        this.identifiant = PersonnePhysiqueId.aleatoire();
        this.id = id;
    }

    public String nom() {
        return nom;
    }

    public String prenom() {
        return prenom;
    }

    public LocalDate dateNaissance() {
        return dateNaissance;
    }

    public NSS nss () {
        return nss;
    }

    public Long id() {
        return id;
    }

    public PersonnePhysiqueId identifiant () {
        return identifiant;
    }

    public String dateNaissanceAsString(){
        return dateNaissance.format(formatter);
    }


    public static PersonnePhysique builder(String nom, String prenom, LocalDate dateNaissance, NSS nss) {
        return new PersonnePhysique(nom,prenom,dateNaissance,nss);
    }

    //hibernate
    private Long id;

    PersonnePhysique(){}

    @Override
    public boolean sameIdentityAs(PersonnePhysique other) {
        return identifiant.equals(other.identifiant());
    }
}
