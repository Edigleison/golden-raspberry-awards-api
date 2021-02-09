package br.com.edigleison.goldenraspberryawardsapi.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.edigleison.goldenraspberryawardsapi.dto.AwardRankingDTO;
import br.com.edigleison.goldenraspberryawardsapi.service.AwardNominationService;

@RestController
@RequestMapping("/ranking")
public class AwardRankingRestController {

	@Autowired
	private AwardNominationService service;
	
	@GetMapping
	public AwardRankingDTO getRanking() {
		return service.getAwardRanking();
	}

}
