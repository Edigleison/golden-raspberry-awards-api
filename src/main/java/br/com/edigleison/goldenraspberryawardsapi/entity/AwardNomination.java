package br.com.edigleison.goldenraspberryawardsapi.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;

import br.com.edigleison.goldenraspberryawardsapi.dto.AwardWinnerDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@SqlResultSetMapping(
	name="awardWinner",
    classes={
        @ConstructorResult(
            targetClass=AwardWinnerDTO.class,
            columns={
                @ColumnResult(name="producer", type=String.class),
                @ColumnResult(name="interval", type=Integer.class),
                @ColumnResult(name="previousWin", type=Integer.class),
                @ColumnResult(name="followingWin", type=Integer.class)
            }
        )
    }
)

@NamedNativeQuery(
		name = "AwardNomination.findAwardWinnerManyTimes", 
		query=  "WITH winners AS (\n" + 
				"	SELECT \n" + 
				"		ROW_NUMBER() OVER (PARTITION BY p.producer ORDER BY n.year) AS seq,\n" + 
				"		p.producer, \n" + 
				"		n.year\n" + 
				"	FROM award_nomination_producers p\n" + 
				"		LEFT JOIN award_nomination n on p.award_nomination_id = n.id\n" + 
				"	WHERE winner is true\n" + 
				"	ORDER BY  n.year, p.producer\n" + 
				")\n" + 
				"SELECT \n" + 
				"	current.producer AS producer,\n" + 
				"	current.year - previous.year AS \"interval\",\n" + 
				"	previous.year AS previousWin,\n" + 
				"	current.year  AS followingWin\n" + 
				"FROM winners AS current\n" + 
				"LEFT JOIN winners AS previous ON (current.seq = (previous.seq + 1) AND current.producer=previous.producer)\n" + 
				"WHERE previous.year IS NOT NULL", 
				resultSetMapping="awardWinner")

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class AwardNomination {
	@Id
	@GeneratedValue
	@EqualsAndHashCode.Include
	private Long id;

	private Integer year;

	private String title;

	private String studios;

	@ElementCollection
	@Column(name="producer")
	private Set<String> producers;
	
	private Boolean winner;
}
