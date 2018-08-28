package ch.globaz.tmmas.rentesservice.domain.model;

import ch.globaz.tmmas.rentesservice.domain.ValueObject;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@ToString
@Getter
public class DossierId implements ValueObject<DossierId>{

    @NotNull(message = "L'identifiant ne peut pas être null")
    private String identifiant;

    public DossierId(String identifiant) {
        this.identifiant = identifiant;
    }

    public static DossierId aleatoire () {
        return new DossierId(UUID.randomUUID().toString());
    }

    public String identifiant() {
        return identifiant;
    }

    @Override
    public boolean sameValueAs(DossierId other) {
        return this.equals(other);
    }

    DossierId(){}
}
