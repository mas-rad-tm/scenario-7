package ch.globaz.tmmas.rentesservice.infrastructure.service;


import ch.globaz.tmmas.rentesservice.infrastructure.dto.PersonnesPhysiqueDto;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


@FeignClient(decode404 = true,value = "personnes-service")
public interface FeignPersonnesPhysiqueService {

	@LoadBalanced
	@RequestMapping(method = RequestMethod.GET, value = "/personnes-service/personnes/{personnesId}")
	ResponseEntity<PersonnesPhysiqueDto> getPersonnePhysiqueById(@PathVariable("personnesId") Long tiersId);

	@LoadBalanced
	@RequestMapping(method = RequestMethod.GET, value = "/personnes-service/personnes?countOnly=true")
	ResponseEntity<List<PersonnesPhysiqueDto>> countPersonnesPhysique();

	@LoadBalanced
	@RequestMapping(method = RequestMethod.GET, value = "/personnes-service/personnes")
	ResponseEntity<List<PersonnesPhysiqueDto>> getPersonnesPhysique();


}
