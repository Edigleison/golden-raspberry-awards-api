package br.com.edigleison.goldenraspberryawardsapi.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import br.com.edigleison.goldenraspberryawardsapi.entity.AwardNomination;
import br.com.edigleison.goldenraspberryawardsapi.service.AwardNominationService;

@Component
public class InitDataBean {

	private static final Logger LOG = Logger.getLogger(InitDataBean.class.getCanonicalName());

	@Autowired
	private AwardNominationService service;

	@PostConstruct
	public void init() {
		LOG.info("*** iniciando carga de registros");

		try {
			List<AwardNomination> nominationList = loadNominationsFromCsvFile();
			service.saveAll(nominationList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected List<AwardNomination> loadNominationsFromCsvFile() throws IOException {
		InputStream file = getCsvFile();

		CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader().withColumnSeparator(';');

		CsvMapper mapper = new CsvMapper();
		MappingIterator<Map<String, String>> it = mapper.readerFor(Map.class).with(bootstrapSchema).readValues(file);

		List<AwardNomination> nomationList = new ArrayList<>();
		while (it.hasNext()) {
			AwardNomination nomination = new AwardNomination();
			Map<String, String> rowAsMap = it.next();
			nomination.setYear(Integer.parseInt(rowAsMap.get("year")));
			nomination.setTitle(rowAsMap.get("title"));
			nomination.setStudios(rowAsMap.get("studios"));

			final String producerNames = rowAsMap.get("producers");
			nomination.setProducers(Arrays.stream(producerNames.split(", and | and |, ")).collect(Collectors.toSet()));

			nomination.setWinner("yes".equals(rowAsMap.get("winner")));

			nomationList.add(nomination);
		}
		
		return nomationList;
	}
	
	protected InputStream getCsvFile() throws IOException {
		return new ClassPathResource("movielist.csv").getInputStream();
	}

}