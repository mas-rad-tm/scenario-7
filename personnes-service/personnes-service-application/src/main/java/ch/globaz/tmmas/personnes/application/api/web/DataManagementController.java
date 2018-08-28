package ch.globaz.tmmas.personnes.application.api.web;

import ch.globaz.tmmas.personnes.application.api.dto.PersonnesPhysiqueDto;
import ch.globaz.tmmas.personnes.application.api.dto.datamanagement.SampleDataDto;
import ch.globaz.tmmas.personnes.application.service.PersonnePhysiqueService;
import ch.globaz.tmmas.personnes.domain.model.NSS;
import ch.globaz.tmmas.personnes.domain.model.PersonnePhysique;
import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/data")
public class DataManagementController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataManagementController.class);

    @Autowired
    PersonnePhysiqueService personneService;

    static final Faker faker = new Faker(new Locale("fr"));
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    DateFormat df = new SimpleDateFormat("dd.MM.yyyy");

    @RequestMapping(value = "/sample",method = RequestMethod.POST)
    public ResponseEntity<String> createPerson(@RequestBody SampleDataDto dto){
        LOGGER.debug("createPerson(), {}",dto);

        Integer nbElements = Integer.valueOf(dto.getNbValeurs());

        IntStream.range(0,nbElements).forEach(iteration -> {
            personneService.sauve(PersonnePhysique.builder(getNom(),getPrenom(),getDateNaissance(),new NSS(getNss())));
        });

        LOGGER.debug("sample personns create, {} elements inserted",nbElements);

        return new ResponseEntity<String>("OK, " + nbElements + " Created",HttpStatus.CREATED);
    }

    private String getNom () {
        return faker.name().lastName();
    }

    private String getPrenom () {
        return faker.name().firstName();
    }

    private LocalDate getDateNaissance () {

        Date date = faker.date().birthday(15,80);

        return LocalDate.parse(df.format(date),formatter);
    }

    private String getNss () {

        StringBuilder nss = new StringBuilder("756.");
        nss.append(faker.number().randomDigit());
        nss.append(faker.number().randomDigit());
        nss.append(faker.number().randomDigit());
        nss.append(faker.number().randomDigit());
        nss.append(".");
        nss.append(faker.number().randomDigit());
        nss.append(faker.number().randomDigit());
        nss.append(faker.number().randomDigit());
        nss.append(faker.number().randomDigit());
        nss.append(".");
        nss.append(faker.number().randomDigit());
        nss.append(faker.number().randomDigit());

        return nss.toString();

    }
}
