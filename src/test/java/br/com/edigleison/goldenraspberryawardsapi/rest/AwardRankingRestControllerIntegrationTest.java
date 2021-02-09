package br.com.edigleison.goldenraspberryawardsapi.rest;

import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import br.com.edigleison.goldenraspberryawardsapi.dto.AwardRankingDTO;
import br.com.edigleison.goldenraspberryawardsapi.dto.AwardWinnerDTO;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class AwardRankingRestControllerIntegrationTest {
	
	@Autowired
    private MockMvc mvc;
	
	@Autowired
	private JacksonTester<AwardRankingDTO> jsonResponse;
	
	@Test
	void givenAwordsNomination_whenGetRanking_thenStatusOKAndExpectedResponse() throws Exception {
		//given
		AwardRankingDTO ranking = new AwardRankingDTO();
		ranking.getMin().add(new AwardWinnerDTO("Joel Silver",1, 1990, 1991));
		ranking.getMax().add(new AwardWinnerDTO("Matthew Vaughn",13, 2002, 2015));

		String expedtedJsonResponse = jsonResponse.write(ranking).getJson();
		
		//when
		MockHttpServletResponse response = mvc
				.perform(get("/ranking").contentType(MediaType.APPLICATION_JSON))
				.andReturn()
				.getResponse();
		
		//then
		then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		then(response.getContentAsString()).isEqualTo(expedtedJsonResponse);
	}

}
