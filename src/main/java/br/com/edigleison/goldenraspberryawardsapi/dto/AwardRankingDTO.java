package br.com.edigleison.goldenraspberryawardsapi.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class AwardRankingDTO {

	private List<AwardWinnerDTO> min = new ArrayList<>();
	private List<AwardWinnerDTO> max = new ArrayList<>();

}
