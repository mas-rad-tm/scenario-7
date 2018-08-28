package ch.globaz.tmmas.personnes.domain.model;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.validation.FieldError;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Null;

import java.util.Set;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.Assert.assertFalse;


public class NSSTest {

    static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory =
                Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void assertThatInstanciationWork(){

        NSS nss = NSSBuilder.aleatoire();

        assertThat(nss.nss()).isNotNull();

    }

    @Test
    public void assertThatNullValueThrowException(){

        NSS nss = new NSS(null);

        Set<ConstraintViolation<NSS>> violations = validator.validate(nss);

        assertTrue(violations.size() != 0);

    }

    @Test
    public void assertThatNssValueCorrectlyGenerated(){

        NSS nss = NSSBuilder.aleatoire();
        System.out.println(nss.nss());

        assertThat(nss.nss()).hasSize(16);

        assertThat(nss.nss().charAt(3)).isEqualTo('.');

        assertThat(nss.nss().charAt(8)).isEqualTo('.');

    }



}