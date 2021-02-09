package br.com.edigleison.goldenraspberryawardsapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AwardWinnerDTO {
	private String producer;
	private Integer inverval;
	private Integer previousWin;
	private Integer followingWin;
}
