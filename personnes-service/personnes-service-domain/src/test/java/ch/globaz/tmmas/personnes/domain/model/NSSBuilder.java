package ch.globaz.tmmas.personnes.domain.model;

import com.github.javafaker.Faker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.stream.IntStream;

public class NSSBuilder {

    static final Faker faker = new Faker(new Locale("fr"));
    static final String NSS_PREFIX_NUMBER = "756";

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    DateFormat df = new SimpleDateFormat("dd.MM.yyyy");


    public static NSS aleatoire() {
        return new NSS(generateNssAleatoire());
    }

    private static String generateNssAleatoire(){

        StringBuilder nss = new StringBuilder(NSS_PREFIX_NUMBER);

        IntStream.range(0, 13)
            .forEach(index -> {

                String position = (index == 0 || index == 5 || index == 10) ? "." :
                        String.valueOf(faker.number().randomDigit());

                nss.append(position);
            }

        );


        return nss.toString();
    }

}
