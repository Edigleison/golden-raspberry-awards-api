package br.com.edigleison.goldenraspberryawardsapi.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.edigleison.goldenraspberryawardsapi.dto.AwardRankingDTO;
import br.com.edigleison.goldenraspberryawardsapi.dto.AwardWinnerDTO;
import br.com.edigleison.goldenraspberryawardsapi.entity.AwardNomination;
import br.com.edigleison.goldenraspberryawardsapi.repository.AwardNominationRepository;

@Service
public class AwardNominationService {

	@Autowired
	private AwardNominationRepository repository;
	
	public AwardRankingDTO getAwardRanking() {
		AwardRankingDTO ranking = new AwardRankingDTO();
		
		List<AwardWinnerDTO> winners = repository.findAwardWinnerManyTimes();
		if(!winners.isEmpty()) {
			List<AwardWinnerDTO> minList = filterMinIntervalWinners(winners);
			ranking.getMin().addAll(minList);
			
			List<AwardWinnerDTO> maxList = filterMaxIntervalWinners(winners);
			ranking.getMax().addAll(maxList);
		}
		
		return ranking;
	}

	public void saveAll(List<AwardNomination> nominee) {
		repository.saveAll(nominee);
	}
	
	private List<AwardWinnerDTO> filterMaxIntervalWinners(List<AwardWinnerDTO> winners) {
		Integer max = winners
				.stream()
				.mapToInt(winner -> winner.getInverval())
				.max()
				.orElseThrow(NoSuchElementException::new);
		
		List<AwardWinnerDTO> maxList = winners
				.stream()
				.filter(winner -> winner.getInverval().equals(max))
				.collect(Collectors.toList());
		
		return maxList;
	}
	
	private List<AwardWinnerDTO> filterMinIntervalWinners(List<AwardWinnerDTO> winners) {
		Integer min = winners
				.stream()
				.mapToInt(winner -> winner.getInverval())
				.min()
				.orElseThrow(NoSuchElementException::new);
		
		List<AwardWinnerDTO> minList = winners
				.stream()
				.filter(winner -> winner.getInverval().equals(min))
				.collect(Collectors.toList());
		
		return minList;
	}
	
}
