package br.com.edigleison.goldenraspberryawardsapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.edigleison.goldenraspberryawardsapi.dto.AwardWinnerDTO;
import br.com.edigleison.goldenraspberryawardsapi.entity.AwardNomination;

@Repository
public interface AwardNominationRepository extends CrudRepository<AwardNomination, Long>{
	
	@Query(nativeQuery = true)
	List<AwardWinnerDTO> findAwardWinnerManyTimes();
};
